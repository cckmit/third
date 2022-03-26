package com.beitie.hashcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/14
 */
public class HashMapModel {

    public static void main(String[] args) {

    }

    public static void getSize(Map map){
        System.out.println("尺寸===="+map.size());
    }

    public static void hashMapTest(){
        Map<String,String> map=new HashMap<>();
        getSize(map);
        map.put("战三","21");
        getSize(map);
        map.put("李四","22");
        getSize(map);
        map.put("王五","23");
        getSize(map);
        map.put("王五1","23");
        getSize(map);
        map.put("王五2","23");
        getSize(map);
        map.put("王五3","23");
        getSize(map);
        map.put("王五4","23");
        getSize(map);
        map.put("王五5","23");
        getSize(map);
        map.put("王五6","23");
        getSize(map);
        map.put("王五6","23");
        getSize(map);
        map.put("王五6","23");
        getSize(map);
    }
}
