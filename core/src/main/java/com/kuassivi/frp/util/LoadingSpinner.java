package com.kuassivi.frp.util;

import rx.Observable;
import rx.functions.Action0;

import static com.kuassivi.frp.util.Logger.log;

public class LoadingSpinner {

    public static boolean isLoading = false;
    public static boolean isLoadingList = false;

    public static String SHOW_LOADING = "/ Show Loading...";
    public static String HIDE_LOADING = "Hide Loading .../";
    public static String SHOW_LOADING_LIST = "/ Show Loading List...";
    public static String HIDE_LOADING_LIST = "Hide Loading List .../";

    public static <T> Observable.Transformer<T, T> attach() {
        return tObservable -> tObservable.doOnSubscribe(LoadingSpinner.showLoading())
                                         .doAfterTerminate(LoadingSpinner.hideLoading())
                                         .doAfterTerminate(LoadingSpinner.hideLoadingList());
    }

    public static void handleLoadingList() {
        hideLoading().call();
        showLoadingList().call();
    }

    public static Action0 showLoading() {
        return () -> {
            if (!isLoading) {
                isLoading = true;
                log(SHOW_LOADING);
            }
        };
    }

    public static Action0 hideLoading() {
        return () -> {
            if (isLoading) {
                isLoading = false;
                log(HIDE_LOADING);
            }
        };
    }

    public static Action0 showLoadingList() {
        return () -> {
            if (!isLoadingList) {
                isLoadingList = true;
                log(SHOW_LOADING_LIST);
            }
        };
    }

    public static Action0 hideLoadingList() {
        return () -> {
            if (isLoadingList) {
                isLoadingList = false;
                log(HIDE_LOADING_LIST);
            }
        };
    }
}
