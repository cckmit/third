package com.beitie.other;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/17
 */
public class ArrayListModel {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String tmp = iter.next();
            System.out.println(tmp);
            if (tmp.equals("3")) {
                list.remove("3");
            }
        }


    }
}
