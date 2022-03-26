package com.beitie.qualifier;

import java.nio.charset.Charset;

/**
 * @author betieforever
 * @description √Ë ˆ
 * @date 2021/12/8
 */
public class Child extends Parent{
    private String nameDefault ="childDefault";
    @Override
    public String toString() {
        return "Child{" +
                "namePublic='" + namePublic + '\'' +
                ", nameProtected='" + nameProtected + '\'' +
                ", nameDefault='" + nameDefault + '\'' +
                '}';
    }

    public Child(String nameDefault) {
        this("msg",nameDefault);
//        this.nameDefault = nameDefault;
    }

    public Child(String msg, String nameDefault) {
        super(msg);
        this.nameDefault = nameDefault;
    }

    public void testPublic(){
        String msg = nameDefault;
        System.out.println("--msg--"+msg);
        System.out.println("-parentnameDefault--"+super.nameDefault);
        super.testPublic();
        System.out.println("child test method");
        Parent.staticMehtod();
    }

    public static void main(String[] args) {
//        Child child = new Child("zhagsan");
//        Child.staticMehtod();
//        Parent.staticMehtod();
//        child.testPublic();
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset().name());
        String msg = "œ˚œ¢";
        System.out.println(msg);
    }

    public static void   staticMehtod(){
        System.out.println("child staticMehtod");
    }
}
