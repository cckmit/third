package com.beitie.util;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/20
 */
public class Test {
    public static void main(String[] args) {
       int t = 0;
       while (t !=13){
           t =getRandomInt();
       }


    }
    //请随机出一个3到13的数值

    private static int getRandomInt(){
        double b =Math.ceil( Math.random()*11+2);
        String str = new String(b+"");
        int a = str.indexOf(".");
        str=str.substring(0,a);
        Integer t = Integer.parseInt(str);
        System.out.println(t);
        return t;
    }
}
