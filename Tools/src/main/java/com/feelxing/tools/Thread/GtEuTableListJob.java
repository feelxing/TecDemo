package com.feelxing.tools.Thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by MEV on 2017/5/12.
 */
public class GtEuTableListJob implements IJob {
    private GtService gtService;
    private int id;
    public GtEuTableListJob(GtService gtService,int id){
        this.gtService=gtService;
        this.id=id;
    }
    @Override
    public void start() {
        gtService.print(id);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
