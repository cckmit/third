package com.beitie.designModel.model;

public class AWorker extends  AbstractWorker {

    @Override
    public void first() {
        System.out.println(this.getClass().getName()+"第一步");
    }

    @Override
    public void second() {
        System.out.println(this.getClass().getName()+"第二步");
    }

    @Override
    public void third() {
        System.out.println(this.getClass().getName()+"第三步");
    }
}
