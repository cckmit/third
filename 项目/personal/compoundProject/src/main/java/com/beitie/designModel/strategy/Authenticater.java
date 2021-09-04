package com.beitie.designModel.strategy;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/1
 */
public class Authenticater {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    public boolean authenticate(String pwd){
        return this.strategy.authenticate(pwd);
    }
}
