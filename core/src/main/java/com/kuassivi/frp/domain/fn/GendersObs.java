package com.kuassivi.frp.domain.fn;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.domain.contract.IGenre;
import rx.Observable;
import rx.functions.Action1;

import static com.kuassivi.frp.domain.fn.Filter.byGenre;

@SuppressWarnings("WeakerAccess")
public class GenresObs {

    public static <T extends IGenre> Observable<T> filterByGenre(Observable<T> observable, Genre genre) {
        return observable.filter((object) -> byGenre(object, genre));
    }

    public static <T extends IGenre> Observable<T> filterByGenreResilient(Observable<T> observable,
                                                                            Genre genre,
                                                                            Action1<Throwable> onErrorAction) {
        return filterByGenreResilient(observable, genre, Observable.empty(), onErrorAction);
    }

    public static <T extends IGenre> Observable<T> filterByGenreResilient(Observable<T> observable,
                                                                            Genre genre,
                                                                            Observable<T> onErrorReturn,
                                                                            Action1<Throwable> onErrorAction) {
        return observable.flatMap(t -> filterByGenre(Observable.just(t), genre)
                .onErrorResumeNext(throwable -> {
                    if (onErrorAction != null)
                        onErrorAction.call(throwable);
                    return onErrorReturn;
                }));
    }
}
