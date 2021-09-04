package com.beitie.designModel.observer;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/1
 */
public class ObserverTest {
    public static void main(String[] args) {
        ConcreteObserver concreteObserver1 = new ConcreteObserver("张三");
        ConcreteObserver concreteObserver2 = new ConcreteObserver("李四");
        Subject subject = new ConcreteSubject();
        subject.addObserver(concreteObserver1);
        subject.addObserver(concreteObserver2);
        subject.notifyAllObservers();
    }
}
