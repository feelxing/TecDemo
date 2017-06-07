package com.feelxing.tools.Thread;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by MEV on 2017/5/13.
 */
public class Main {
    public static void main(String[] args) {
        GtEuTableListJob euTableListJob=new GtEuTableListJob(new GtService(),1);
        GtEuTableListJob euTableListJob2=new GtEuTableListJob(new GtService(),2);
        if (Tasks._jobQueue == null) {
            Tasks._jobQueue = new ConcurrentLinkedQueue<IJob>();
        }
        Tasks._jobQueue.add(euTableListJob);
        Tasks._jobQueue.add(euTableListJob2);
        Tasks tasks=new Tasks();
        tasks.start();
    }
}
