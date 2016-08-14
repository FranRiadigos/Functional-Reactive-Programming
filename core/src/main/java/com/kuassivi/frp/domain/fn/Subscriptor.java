package com.kuassivi.frp.domain.fn;

import rx.Observable;
import rx.functions.Action1;


public class Subscriptor {

    public static <T> Observable.Transformer<T, T> onEach(Action1<T> action1) {
        return observable ->
                observable.doOnEach(notification -> {
                    if (notification.hasValue()) {
                        T value = (T) notification.getValue();
                        action1.call(value);
                    }
                });
    }
}
