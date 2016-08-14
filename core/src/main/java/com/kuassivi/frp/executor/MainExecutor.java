package com.kuassivi.frp.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class MainExecutor implements Executor {

    private BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    /**
     * Executor stuff
     */

    public void runLoop() throws InterruptedException {
        while (!Thread.interrupted()) {
            tasks.take().run();
        }
    }

    @Override
    public void execute(Runnable command) {
        tasks.add(command);
    }

    public void shutdown() {
        Thread currentThread = Thread.currentThread();
        if(currentThread.isAlive() && !currentThread.isInterrupted()) {
            currentThread.interrupt();
        }
    }
}
