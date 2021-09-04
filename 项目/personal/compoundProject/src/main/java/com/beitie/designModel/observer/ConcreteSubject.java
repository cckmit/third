package com.beitie.designModel.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/1
 */
public class ConcreteSubject implements  Subject{
    private List<Observer> observers =new ArrayList<>();

    public List<Observer> addObserver(Observer observer){
        observers.add(observer);
        return observers;
    }

    public List<Observer> removeObserver(Observer observer){
        observers.remove(observer);
        return observers;
    }

    public void notifyAllObservers(){
        observers.forEach(observer -> observer.notice());
    }
}
