package com.beitie.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/31
 */
public class RunnableTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            service.execute(new MyRunnable(i));
        }
    }
}
