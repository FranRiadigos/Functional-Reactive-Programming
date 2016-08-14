package com.kuassivi.frp.api;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerAPI {

    private PlayerListener listener;

    public void attachListener(PlayerListener listener) {
        this.listener = listener;
    }

    public void play() {
        long delay = 0, period = 1, videoEndsAt = 10;
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = new Runnable() {
            private AtomicInteger position = new AtomicInteger(1);
            @Override
            public void run() {
                if(position.get() == videoEndsAt) {
                    listener.onPlaybackCompleted();
                    service.shutdown();
                } else {
                    listener.onTimeUpdated(position.getAndIncrement());
                }
            }
        };
        service.scheduleAtFixedRate(runnable, delay, period, TimeUnit.SECONDS);
    }
}
