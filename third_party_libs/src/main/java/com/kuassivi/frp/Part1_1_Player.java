package com.kuassivi.frp;

import rx.schedulers.Schedulers;

import static com.kuassivi.frp.util.Logger.log;
import static com.kuassivi.frp.util.Logger.showThread;

public class Part1_1_Player {

    public static void main(String[] args) {
        Rx1_1_PlayerDecorator playerDecorator = new Rx1_1_PlayerDecorator();

        playerDecorator.notifyOnTime(2, 7);

        playerDecorator.play()
                       .observeOn(Schedulers.computation())
                       .subscribe(sec -> {
                                   showThread();
                                   log("Emitting on " + sec);
                               },
                               Throwable::printStackTrace,
                               () -> log("Playback completed"));
    }
}
