package com.kuassivi.frp.api;

import com.kuassivi.frp.util.ThreadUtil;

import java.util.Timer;

public class PlayerEventAPI {
    private PlayerListener listener;
    private Timer playerTimer;

    public void attachListener(PlayerListener listener) {
        this.listener = listener;
    }

    public void initialise() {
        ThreadUtil.postpone(() -> listener.onInit(), 500);
    }

    public void play() {
        listener.onPlay();
        playerTimer = ThreadUtil.postpone(this::stop, 2000);
    }

    public void pause() {
        listener.onPause();
        playerTimer.cancel();
    }

    public void stop() {
        listener.onStop();
    }
}
