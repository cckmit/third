package com.beitie.designModel.dutyChain;

public class HandlerTest {
    public static void main(String[] args) {
        AHandler aHandler = new AHandler(AbstractHandler.DEBUG);
        BHandler bHandler = new BHandler(AbstractHandler.INFO);
        CHandler cHandler = new CHandler(AbstractHandler.ERROR);
        aHandler.setNextHandler(bHandler);
        bHandler.setNextHandler(cHandler);
        aHandler.handle(AbstractHandler.ERROR,"sss");
    }
}
