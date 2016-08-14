package com.kuassivi.frp;

import com.kuassivi.frp.executor.MainExecutor;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        MainExecutor mainExecutor = new MainExecutor();
        Scheduler main = Schedulers.from(mainExecutor);

        mainExecutor.runLoop();
    }
}
