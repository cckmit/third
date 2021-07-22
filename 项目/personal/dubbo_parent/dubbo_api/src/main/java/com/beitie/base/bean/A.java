package com.beitie.base.bean;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/24
 */
class A{
    public static void  show(){
        System.out.println(" Static Method of A");
    }
    public void read(){
        System.out.println("read A");
    }
}
class B extends A{
    public static void show(){
        System.out.println("Static Method of B");

    }
    public void read(){
        System.out.println("read B");
    }

    public static void main(String[] args) {
        A x=new B();x.show();x.read();
        B y=new B();y.show();y.read();
    }
}
