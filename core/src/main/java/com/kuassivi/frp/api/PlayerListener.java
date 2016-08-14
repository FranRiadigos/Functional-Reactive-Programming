package com.kuassivi.frp.api;

public interface PlayerListener {
    void onInit();
    void onPlay();
    void onPause();
    void onStop();
    void onTimeUpdated(int sec);
    void onPlaybackCompleted();
}
