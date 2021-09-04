package com.beitie.designModel.observer;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/1
 */
public class ConcreteObserver implements  Observer{
    private String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void notice() {
        System.out.println(this.name + "被通知了");
    }
}
