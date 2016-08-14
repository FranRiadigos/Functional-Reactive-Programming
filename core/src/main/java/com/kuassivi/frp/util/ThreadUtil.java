package com.kuassivi.frp.util;

import rx.SingleSubscriber;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadUtil {

    public static void sleep(int min, int max) {
        sleep(randInt(min, max));
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Timer postpone(Runnable runnable, int millis) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
                timer.cancel();
            }
        };
        timer.schedule(task, millis);
        return timer;
    }

    public static String getCurrentThreadName() {
        String name = Thread.currentThread().getName();
        return "Thread: " + name;
    }

    public static void handleError(SingleSubscriber subscriber) {
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            subscriber.onError(exception);
        });
    }

    private static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
