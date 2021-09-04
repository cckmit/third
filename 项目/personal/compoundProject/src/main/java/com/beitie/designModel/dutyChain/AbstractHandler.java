package com.beitie.designModel.dutyChain;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/3
 */
public abstract class AbstractHandler implements Handler{
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;
    private int level;
    private Handler nextHandler;

    public Handler setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    @Override
    public void handle(int level,String msg) {
        if(this.level <= level){
            write(msg);
        }
        if(this.nextHandler!=null){
            this.nextHandler.handle(level,msg);
        }
    }

    public abstract void write(String msg) ;
}
