package com.kuassivi.frp.domain.fn;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func2;

public class ScanOperatorSample<R, T> implements Observable.Operator<R, T> {

    private final R initialValue;
    private Func2<R, ? super T, R> accumulator;
    private Action1<Throwable> errorHandler;

    public ScanOperatorSample(R initialValue, Func2<R, ? super T, R> accumulator,
                              Action1<Throwable> errorHandler) {
        this.initialValue = initialValue;
        this.accumulator = accumulator;
        this.errorHandler = errorHandler;
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super R> child) {
        return new Subscriber<T>() {

            private R value = initialValue;

            @Override
            public void onCompleted() {
                child.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                child.onError(e);
            }

            @Override
            public void onNext(T currentValue) {
                R v = value;
                try {
                    v = accumulator.call(v, currentValue);
                } catch (Throwable e) {
                    errorHandler.call(e);
                    return;
                }
                value = v;
                child.onNext(v);
            }
        };
    }
}
