package com.beitie.designModel.strategy;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/1
 */
public class StrategyTest {
    public static void main(String[] args) {
        Authenticater auth= new Authenticater();
        auth.setStrategy(new StrategyA());
        String aPwd="aPwd";
        auth.authenticate(aPwd);
        auth.setStrategy(new StrategyB());
        String bPwd="bPwd";
        auth.authenticate(bPwd);
    }
}
