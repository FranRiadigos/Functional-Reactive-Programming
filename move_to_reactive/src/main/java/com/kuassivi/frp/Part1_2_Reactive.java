package com.kuassivi.frp;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.executor.MainExecutor;
import com.kuassivi.frp.service.DataBase;
import com.kuassivi.frp.service.VideoService;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static com.kuassivi.frp.util.Logger.log;

@SuppressWarnings({"Convert2MethodRef", "WeakerAccess"})
public class Part1_2_Reactive {

    static MainExecutor mainExecutor = new MainExecutor();
    static Scheduler main;

    public static void main(String[] args) throws InterruptedException {

        Part1_2_Reactive app = new Part1_2_Reactive();

        main = Schedulers.from(mainExecutor);

        app.onCreate();

        mainExecutor.runLoop();
    }

    private void onCreate() {

        VideoService videoService = new VideoService();

        videoService.filterBy(Genre.ADVENTURE)
                    // It reacts on changes
                    .map(DataBase::checkAndUpdateForFavourite)
                    .observeOn(main)
                    .doAfterTerminate(mainExecutor::shutdown)
                    .subscribe(
                            videoVM -> log("Emit " + videoVM),
                            Throwable::printStackTrace,
                            () -> log("- Fetching favourites videos done!"));
    }
}
