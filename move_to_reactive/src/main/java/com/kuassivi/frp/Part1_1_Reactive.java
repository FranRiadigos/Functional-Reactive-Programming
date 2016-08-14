package com.kuassivi.frp;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.executor.MainExecutor;
import com.kuassivi.frp.service.VideoService;
import com.kuassivi.frp.util.ThreadUtil;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static com.kuassivi.frp.util.Logger.log;
import static com.kuassivi.frp.util.Logger.newLine;

@SuppressWarnings({"Convert2MethodRef", "WeakerAccess"})
public class Part1_1_Reactive {

    static MainExecutor mainExecutor = new MainExecutor();
    static Scheduler main;

    public static void main(String[] args) throws InterruptedException {

        Part1_1_Reactive app = new Part1_1_Reactive();

        main = Schedulers.from(mainExecutor);

        app.onCreate();

        mainExecutor.runLoop();
    }

    private void onCreate() {

        VideoService videoService = new VideoService();

        videoService.filterBy(Genre.ADVENTURE)
                    .observeOn(main)
                    .subscribe(
                            videoVM -> log("Emit " + videoVM),
                            Throwable::printStackTrace,
                            () -> {
                                log("- Fetching videos by ADVENTURE done!");
                                newLine();
                            });

        ThreadUtil.postpone(() -> {
            videoService.filterBy(Genre.DRAMA)
                        .doOnSubscribe(() -> log("Elapsed time 5 sec"))
                        .observeOn(main)
                        .doAfterTerminate(() -> mainExecutor.shutdown())
                        .subscribe(
                                videoVM -> log("Emit " + videoVM),
                                Throwable::printStackTrace,
                                () -> log("- Fetching videos by DRAMA done!"));
        }, 5000);
    }
}
