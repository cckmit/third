package com.beitie.thread;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/31
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service= Executors.newCachedThreadPool();
        ArrayList<Future<String>> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(service.submit(new CallableThread(i)));
        }
        for (Future<String> fs : list) {
            if(fs.isDone()){
                System.out.println(fs.get());
            }else{
                System.out.println("Future result is not yet complete");
            }
        }
        service.shutdown();
    }
}
