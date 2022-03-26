package com.beitie.thread;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/16
 */
public class VolatileModel2 {

        private boolean flag = false;

        class ThreadOne implements Runnable {
            @Override
            public void run() {
                while (!flag) {
                    System.out.println("执行操作");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("任务停止");
            }
        }

        class ThreadTwo implements Runnable {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000L);
                    System.out.println("flag 状态改变");
                    flag = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            VolatileModel2 testVolatile = new VolatileModel2();
            Thread thread1 = new Thread(testVolatile.new ThreadOne());
            Thread thread2 = new Thread(testVolatile.new ThreadTwo());
            thread1.start();
            thread2.start();
        }
}
