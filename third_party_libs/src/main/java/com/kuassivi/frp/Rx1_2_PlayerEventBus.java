package com.kuassivi.frp;

import com.kuassivi.frp.api.PlayerEventAPI;
import com.kuassivi.frp.api.PlayerListener;
import com.kuassivi.frp.event.InitEvent;
import com.kuassivi.frp.event.PauseEvent;
import com.kuassivi.frp.event.PlayEvent;
import com.kuassivi.frp.event.StopEvent;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class Rx1_2_PlayerEventBus {
    private static Rx1_2_PlayerEventBus instance;
    private PlayerEventAPI player = new PlayerEventAPI();
    private Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public static Rx1_2_PlayerEventBus getInstance() {
        if(instance == null) {
            instance = new Rx1_2_PlayerEventBus();
        }
        return instance;
    }

    private Rx1_2_PlayerEventBus() {
        player.attachListener(new PlayerListener() {
            @Override
            public void onInit() {
                bus.onNext(new InitEvent());
            }

            @Override
            public void onPlay() {
                bus.onNext(new PlayEvent());
            }

            @Override
            public void onPause() {
                bus.onNext(new PauseEvent());
            }

            @Override
            public void onStop() {
                bus.onNext(new StopEvent());
            }

            @Override
            public void onTimeUpdated(int sec) {
            }

            @Override
            public void onPlaybackCompleted() {
            }
        });
    }

    public PlayerEventAPI getPlayer() {
        return player;
    }

    public <R> Observable<R> ofEvent(Class<R> clazz) {
        return bus.ofType(clazz);
    }
}
