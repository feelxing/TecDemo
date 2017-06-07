package com.feelxing.tools.Thread;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by MEV on 2017/5/12.
 */
public class Worker implements Runnable{
    private IJob job;
    public Worker(IJob job){
        this.job=job;
    }
    @Override
    public void run() {
        job.start();
    }
}
