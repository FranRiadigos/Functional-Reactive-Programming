package com.kuassivi.frp;

import static com.kuassivi.frp.util.Logger.log;

public class Part1_3_Combine {

    public static void main(String[] args) {

        Rx1_0_DrmWrapper drmWrapper = new Rx1_0_DrmWrapper("#secret_key#");
        Rx1_1_PlayerDecorator playerDecorator = new Rx1_1_PlayerDecorator();

        playerDecorator.notifyOnTime(4, 6);

        playerDecorator.play()
                       .delaySubscription(drmWrapper.asSingle().toObservable())
                       .subscribe(
                               sec -> log("Emitting on " + sec),
                               Throwable::printStackTrace,
                               () -> log("Playback completed"));
    }
}
