package com.beitie.qualifier;

import java.nio.charset.Charset;

/**
 * @author betieforever
 * @description 描述 测试修饰符的使用
 * @date 2021/12/8
 */

public class Parent {
    public String namePublic = "namePublic";
    protected String nameProtected = "nameProtected";
    String nameDefault = "nameDefault";
    private String namePrivate = "namePrivate";

    public String msg ;

    public Parent() {
        System.out.println("G");
        System.out.println("parent no param constructor");
    }

    public Parent(String msg){
        System.out.println("parent param constructor");
        this.msg = msg;
    }
    public void testPublic(){
        System.out.println("parent test method");
    }

    public static void   staticMehtod(){
        System.out.println("parent staticMehtod");
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset().name());
        String msg = "消息";
        System.out.println(msg);
    }

}
