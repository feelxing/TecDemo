package com.feelxing.tools.Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MEV on 2017/5/13.
 */
public class ThreadPool {
    public static ExecutorService pool ;
    public static ExecutorService getService(){
        if(pool==null){
            pool=Executors.newFixedThreadPool(1);
        }
        return  pool;
    }
}
