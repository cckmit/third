package com.beitie.springboot_quick001.service;

import org.junit.Test;

public class LamdaTest {
    public interface MathOperation{
        void operate(int a,int b);
    };
    public interface GreteService{
        void say();
    }
    @Test
    public static void main(String[] args) {
        MathOperation mathOperation=(a,b)  -> {System.out.println(a*b);
        System.out.println(123);};
        mathOperation.operate(1,2);
        GreteService greteService=() -> System.out.println("hello,world");
        greteService.say();
    }
}
