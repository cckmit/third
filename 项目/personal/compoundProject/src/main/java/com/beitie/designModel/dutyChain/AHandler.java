package com.beitie.designModel.dutyChain;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/3
 */
public class AHandler extends AbstractHandler{

    public AHandler(int level) {
        this.level=level;
    }

    @Override
    public void business(String msg) {
        System.out.println("-----正在进行A项任务-----");
    }
}
