package com.beitie.designModel.dutyChain;

public class CHandler extends AbstractHandler{
    public CHandler(int level) {
        this.level = level;
    }

    @Override
    public void business(String msg) {
        System.out.println("-----正在进行C项任务-----");
    }
}
