package com.beitie.designModel.dutyChain;

public class BHandler extends AbstractHandler{
    public BHandler(int level) {
        this.level=level;
    }
    @Override
    public void business(String msg) {
        System.out.println("-----正在进行B项任务-----");
    }
}
