package com.beitie.designModel.adapter;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/30
 */
public class AdapterExecuter {
    public static void main(String[] args) {
        new Thread(new AdapterSampler(new MyTask())).start();
    }
}
