package com.kuassivi.frp;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.executor.MainExecutor;
import com.kuassivi.frp.service.DataBase;
import com.kuassivi.frp.service.VideoService;
import com.kuassivi.frp.util.Profiler;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class Part1_1_Parallel {

    static MainExecutor mainExecutor = new MainExecutor();
    static Scheduler main;

    static int DEFAULT_WATCHERS = 0;

    public static void main(String[] args) throws InterruptedException {

        Part1_1_Parallel app = new Part1_1_Parallel();

        main = Schedulers.from(mainExecutor);

        app.onCreate();

        mainExecutor.runLoop();
    }

    private void onCreate() {
        Profiler profiler = Profiler.getInstance();

        VideoService videoService = new VideoService();
        videoService.filterBy(Genre.ADVENTURE)
                    .flatMap(
                            videoModel ->
                            Observable.just(videoModel)
                                      .observeOn(Schedulers.io())
                                      .map(DataBase::updateWithFavourite))
                    .toSortedList()
                    .flatMapIterable(videoModel -> videoModel)
                    .observeOn(main)
                    .doOnSubscribe(profiler::start)
                    .doOnCompleted(profiler::get)
                    .doAfterTerminate(mainExecutor::shutdown)
                    .subscribe(System.out::println);
    }
}
