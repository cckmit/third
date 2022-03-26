package com.beitie.other;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/15
 */
public class ArraysOrder {
    public static void main(String[] args) {
        Integer [] a = new Integer[5];
        a[0]=8;
        a[1]=5;
        a[2]=12;
        a[3]=4;
        a[4]=16;
        List list=new ArrayList(Arrays.asList(a));
        Collections.sort(list);
        Iterator<Integer> iterator=list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
//            iterator.remove();
        }
        ListIterator<Integer> listIterator=list.listIterator();
        int i=1;
        while (listIterator.hasNext()) {
            listIterator.add(i);
        }
        System.out.println(list.size());

    }
}
