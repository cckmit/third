package com.beitie.designModel.dutyChain;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/3
 */
public interface Handler {

    void setNextHandler(Handler handler);
    void handle(int level,String msg);
    void business(String msg);
}
