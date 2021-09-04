package com.beitie.designModel.strategy;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/1
 */
public class StrategyA implements  Strategy{
    @Override
    public boolean authenticate(String pwd) {
        System.out.println("--StrategyA---"+pwd);
        return false;
    }
}
