package com.beitie.designModel.adapter;

import java.util.concurrent.Callable;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/30
 */
public class MyTask implements Callable<Long> {
    @Override
    public Long call() throws Exception {
        System.out.println(1l);
        return 1l;
    }
}

