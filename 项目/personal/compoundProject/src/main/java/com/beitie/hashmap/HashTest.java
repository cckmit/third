package com.beitie.hashmap;

/**
 * @author betieforever
 * @description ÃèÊö
 * @date 2021/12/20
 */
public class HashTest {
    final String ss;

    public HashTest(String ss) {
        this.ss = ss;
    }

    public static void main(String[] args) {
        String s ="a";
        Integer code = s.hashCode();
        int a= code ^ code >>> 16;
        System.out.println(a);
    }
}
