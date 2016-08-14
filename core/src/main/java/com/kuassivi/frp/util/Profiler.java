package com.kuassivi.frp.util;

public class Profiler {

    private static Profiler instance;

    public static Profiler getInstance() {
        return instance == null ? (instance = new Profiler()) : instance;
    }

    private static String TIME_SPENT = "Time spent: %s.%03d sec";

    private long              startDate ;
    private long              startNanoseconds ;

    public void start() {
        this.startDate = System.currentTimeMillis();
        this.startNanoseconds = System.nanoTime();
    }

    public void get() {
        printFormatted();
        start();
    }

    public void accumulate() {
        printFormatted();
    }

    private void printFormatted() {
        long microSeconds = (System.nanoTime() - this.startNanoseconds) / 1000;
        long date = (System.currentTimeMillis() - this.startDate) / 1000;
        System.out.println(String.format(TIME_SPENT, date, microSeconds % 1000));
    }
}
