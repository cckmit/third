package com.beitie.designModel.model;

public class ModelTest {
    public static void main(String[] args) {
        AbstractWorker workerA=new AWorker();
        workerA.work();
        AbstractWorker workerB=new BWorker();
        workerB.work();
    }
}
