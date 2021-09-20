package com.beitie.designModel.construct;

/**
 * 建造者模式
 */
public class CarBuilder {
    private Car car;
    private Body body;
    private Engine engine;
    private static CarBuilder carBuilder;
    public  void addCar(Car car){
        this.car=car;
    }

    private CarBuilder() {
    }

    public static CarBuilder newInstance(){
        if (carBuilder == null) return  new CarBuilder();
        else return  carBuilder;
    }
}
