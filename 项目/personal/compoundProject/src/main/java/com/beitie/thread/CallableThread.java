package com.beitie.thread;

import java.util.concurrent.Callable;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/31
 */
public class CallableThread implements Callable<String>{
    public static void main(String[] args) {
    }
    private int id;

    public CallableThread(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        return "中国人-------"+id;
    }
}
