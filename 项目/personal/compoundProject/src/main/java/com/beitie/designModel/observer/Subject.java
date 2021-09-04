package com.beitie.designModel.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/1
 */
public interface Subject {

     List<Observer> addObserver(Observer observer);

     List<Observer> removeObserver(Observer observer);

     void notifyAllObservers();
}
