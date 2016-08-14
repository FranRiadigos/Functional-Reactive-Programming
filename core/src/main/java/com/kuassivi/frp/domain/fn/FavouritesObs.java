package com.kuassivi.frp.domain.fn;

import com.kuassivi.frp.domain.contract.IFavourite;
import com.kuassivi.frp.service.DataBase;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FavouritesObs {

    public static <T extends IFavourite> Observable<T> checkFavouritesFrom(Observable<T> observable) {
        return checkFavouritesFrom(observable, Schedulers.io());
    }

    public static <T extends IFavourite> Observable<T> checkFavouritesFrom(Observable<T> observable,
                                                                         Scheduler scheduler) {
        return observable.observeOn(scheduler)
                         .flatMap(object -> updateWithFavouriteParallel(object, scheduler))
                         .toSortedList()
                         .flatMapIterable(videoModel -> videoModel);
    }

    public static <T extends IFavourite> Observable<T> checkFavouritesResilient(Observable<T> observable,
                                                                              Action1<Throwable> onErrorAction) {
        return checkFavouritesResilient(observable, Schedulers.io(), Observable.empty(), onErrorAction);
    }

    public static <T extends IFavourite> Observable<T> checkFavouritesResilient(Observable<T> observable,
                                                                              Scheduler scheduler,
                                                                              Observable<T> onErrorReturn,
                                                                              Action1<Throwable> onErrorAction) {
        return observable.observeOn(scheduler)
                         .flatMap(object ->
                                 updateWithFavouriteParallel(object, scheduler)
                                         .onErrorResumeNext(throwable -> {
                                             if (onErrorAction != null)
                                                 onErrorAction.call(throwable);
                                             return onErrorReturn;
                                         }))
                         .toSortedList()
                         .flatMapIterable(videoModel -> videoModel);
    }

    public static <T extends IFavourite> Observable<T> updateWithFavouriteParallel(T object, Scheduler scheduler) {
        return Observable.just(object)
                         .observeOn(scheduler)
                         .map(DataBase::updateWithFavourite);
    }
}
