package com.kuassivi.frp.domain.fn;

import com.kuassivi.frp.domain.contract.IWatcher;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class WatchersObs {

    public static Observable<Integer> extractWatchersFrom(Observable<? extends IWatcher> observable, int initialValue) {
        return extractWatchersFrom(observable, initialValue, Schedulers.computation());
    }

    public static Observable<Integer> extractWatchersFrom(Observable<? extends IWatcher> observable,
                                                         int initialValue, Scheduler scheduler) {
        return observable.observeOn(scheduler).reduce(initialValue, Accumulator::byWatchers);
    }

    public static Observable<Integer> extractWatchersResilient(Observable<? extends IWatcher> observable,
                                                              int initialValue,
                                                              Action1<Throwable> errorHandler) {
        return extractWatchersResilient(observable, initialValue, Schedulers.computation(), errorHandler);
    }

    public static Observable<Integer> extractWatchersResilient(Observable<? extends IWatcher> observable,
                                                              int initialValue,
                                                              Scheduler scheduler,
                                                              Action1<Throwable> errorHandler) {
        return observable.observeOn(scheduler)
                         .lift(new ScanOperatorSample<Integer, IWatcher>(
                                 initialValue, Accumulator::byWatchers, errorHandler))
                         .takeLast(1);
    }
}
