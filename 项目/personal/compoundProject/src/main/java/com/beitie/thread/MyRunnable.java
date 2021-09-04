package com.beitie.thread;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/31
 */
public class MyRunnable implements Runnable{
    private int i;

    public MyRunnable(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println("中国人==="+i);
    }
}
