package com.beitie.designModel.dutyChain;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/3
 */
public interface Handler {

    Handler setNextHandler();
    void handle(int level,String msg);
    void write(String msg);
}
