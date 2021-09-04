package com.beitie.lamda;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/29
 */
public class lamdaTest1 {
    public interface JiSuan<T>{
        default Integer jisuan(){
            return 23;
        };
        Integer jisuan(int a,int b);
    }
    public interface HelloService{

    }


    public static void main(String[] args) {
        JiSuan<Integer> jiSuan =(a,b) -> {
            return a*b;
        };
        System.out.println(jiSuan.jisuan());
        System.out.println(jiSuan.jisuan(2,3));
    }
}
