package com.kuassivi.frp;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.domain.fn.FavouritesObs;
import com.kuassivi.frp.domain.fn.GenresObs;
import com.kuassivi.frp.domain.fn.WatchersObs;
import com.kuassivi.frp.domain.model.VideoViewModel;
import com.kuassivi.frp.executor.MainExecutor;
import com.kuassivi.frp.service.VideoRepository;
import com.kuassivi.frp.util.LoadingSpinner;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.kuassivi.frp.util.Logger.log;

public class Part1_3_Merge {

    static MainExecutor mainExecutor = new MainExecutor();
    static Scheduler main;

    static int DEFAULT_WATCHERS = 0;

    public static void main(String[] args) throws InterruptedException {

        Part1_3_Merge app = new Part1_3_Merge();

        main = Schedulers.from(mainExecutor);

        app.onCreate();

        mainExecutor.runLoop();
    }

    private void onCreate() {

        VideoRepository repository = new VideoRepository();

        Observable<VideoViewModel> obFiltered;
        Observable<VideoViewModel> obFavourites;
        Observable<Integer> obWatchers;
        Observable<Object> merged;

        Observable<VideoViewModel> obVideos = repository.getVideos();

        obFiltered = GenresObs.filterByGenre(obVideos, Genre.ADVENTURE).share();

        obFavourites = FavouritesObs.checkFavouritesFrom(obFiltered)
                                  .observeOn(main)
                                  .doOnCompleted(videosCompleted());

        obWatchers = WatchersObs.extractWatchersFrom(obFiltered, DEFAULT_WATCHERS)
                                .observeOn(main)
                                .doOnCompleted(watchersCompleted());

        merged = Observable.merge(obFavourites, obWatchers)
                           .ofType(Object.class)
                           .share()
                           .compose(LoadingSpinner.attach())
                           .doAfterTerminate(mainExecutor::shutdown);

        merged.ofType(VideoViewModel.class).subscribe(showVideos());

        merged.ofType(Integer.class).subscribe(showWatchers());
    }

    private <T extends VideoViewModel> Action1<T> showVideos() {
        return videoVM -> {
            log("Emit " + videoVM);
            //LoadingSpinner.handleLoadingList();
        };
    }

    private <T extends Integer> Action1<T> showWatchers() {
        return watchers -> log("Emit users viewing ADVENTURE videos: " + watchers);
    }

    private Action0 videosCompleted() {
        return () -> log("- Fetching favourites videos done!");
    }

    private Action0 watchersCompleted() {
        return () -> {
            log("- Fetching watchers done!");
            //LoadingSpinner.handleLoadingList();
        };
    }
}
