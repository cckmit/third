package com.beitie.lamda;

import java.util.ArrayList;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/9/1
 */
public class ForeachTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.forEach(item -> System.out.println(item) );
    }
}
