package com.kuassivi.frp;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.domain.model.VideoViewModel;
import com.kuassivi.frp.executor.MainExecutor;
import com.kuassivi.frp.service.VideoRepository;
import com.kuassivi.frp.util.LoadingSpinner;
import com.kuassivi.frp.util.Profiler;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.kuassivi.frp.domain.fn.FavouritesObs.checkFavouritesResilient;
import static com.kuassivi.frp.domain.fn.GenresObs.filterByGenreResilient;
import static com.kuassivi.frp.domain.fn.WatchersObs.extractWatchersResilient;
import static com.kuassivi.frp.util.Logger.log;

public class Part1_2_Resiliency {

    static MainExecutor mainExecutor = new MainExecutor();
    static Scheduler main;
    static Profiler profiler = new Profiler();

    static int DEFAULT_WATCHERS = 0;

    public static void main(String[] args) throws InterruptedException {

        Part1_2_Resiliency app = new Part1_2_Resiliency();

        main = Schedulers.from(mainExecutor);

        app.onCreate();

        mainExecutor.runLoop();
    }

    public void onCreate() {

        VideoRepository repository = new VideoRepository();

        Observable<VideoViewModel> obFiltered;
        Observable<VideoViewModel> obFavourites;
        Observable<Integer> obWatchers;
        Observable<Object> merged;

        Observable<VideoViewModel> obVideos = repository.getVideosWithErrors();
        obFiltered = filterByGenreResilient(obVideos, Genre.ACTION, notifyError()).share();
        obFavourites = checkFavouritesResilient(obFiltered, notifyError());
        obWatchers = extractWatchersResilient(obFiltered, DEFAULT_WATCHERS, notifyError());
        merged = mergeAndAttachLoading(obFavourites, obWatchers);
        merged.ofType(VideoViewModel.class).subscribe(showVideos());
        merged.ofType(Integer.class).subscribe(showWatchers());
    }

    private Observable<Object> mergeAndAttachLoading(Observable<VideoViewModel> obFavourites,
                                                     Observable<Integer> obWatchers) {
        return Observable.merge(obFavourites, obWatchers)
                         .ofType(Object.class)
                         .share()
                         .observeOn(main)
                         .doOnSubscribe(profiler::start)
                         .doOnTerminate(profiler::get)
                         .compose(LoadingSpinner.attach())
                         .doAfterTerminate(mainExecutor::shutdown);
    }

    private <T extends VideoViewModel> Action1<T> showVideos() {
        return VideoVM -> log("Emit " + VideoVM);
    }

    private <T extends Integer> Action1<T> showWatchers() {
        return watchers -> log("Emit users viewing ACTION videos: " + watchers);
    }

    private Action1<Throwable> notifyError() {
        return throwable -> log(throwable + " " + throwable.getStackTrace()[0]);
    }
}
