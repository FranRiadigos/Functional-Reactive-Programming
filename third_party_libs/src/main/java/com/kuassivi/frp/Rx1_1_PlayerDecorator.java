package com.kuassivi.frp;

import com.kuassivi.frp.api.PlayerAPI;
import com.kuassivi.frp.api.PlayerListener;
import com.kuassivi.frp.util.Logger;
import rx.Observable;
import rx.Subscriber;

import java.util.Arrays;

public class Rx1_1_PlayerDecorator {

    private Integer[] interestedPositions;

    public void notifyOnTime(Integer... positions) {
        this.interestedPositions = Arrays.copyOf(positions, positions.length);
        Arrays.sort(this.interestedPositions);
    }

    public Observable<Integer> play() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                PlayerAPI player;
                try {
                    player = new PlayerAPI();
                    player.attachListener(new PlayerListener() {
                        @Override
                        public void onTimeUpdated(int sec) {
                            Logger.showThread();
                            if(interestedPositions != null
                               && interestedPositions.length > 0) {
                                if(Arrays.binarySearch(interestedPositions, sec) >= 0) {
                                    subscriber.onNext(sec);
                                }
                            } else {
                                // Otherwise notify each position
                                subscriber.onNext(sec);
                            }
                        }

                        @Override
                        public void onPlaybackCompleted() {
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onInit() {}

                        @Override
                        public void onPlay() {}

                        @Override
                        public void onPause() {}

                        @Override
                        public void onStop() {}
                    });
                    player.play();
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }
}
