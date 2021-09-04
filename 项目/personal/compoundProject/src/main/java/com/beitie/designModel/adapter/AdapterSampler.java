package com.beitie.designModel.adapter;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/30
 */
public class AdapterSampler implements Runnable{
    public  MyTask myTask;

    public AdapterSampler(MyTask myTask){
        this.myTask=myTask;
    }

    @Override
    public void run() {
        try {
            myTask.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
