package com.beitie.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/16
 */
public class VolatileModel {
        public static AtomicInteger t = new AtomicInteger(0);

        public static void main(String[] args){

            Thread[] threads = new Thread[10];
            for(int i = 0; i < 10; i++){
                //每个线程对t进行1000次加1的操作
                threads[i] =new Thread(new Runnable(){
                    @Override
                    public void run(){
                        for(int j = 0; j < 10000; j++){
                            t.getAndIncrement();
                        }
                    }
                });
                threads[i].start();
            }
            System.out.println("所有线程均已运行");
            //等待所有累加线程都结束
            while(Thread.activeCount() > 1){
                System.out.println("当前线程存货个数"+Thread.activeCount());
                Thread.yield();
            }

            //打印t的值
            System.out.println(t);
        }
}
