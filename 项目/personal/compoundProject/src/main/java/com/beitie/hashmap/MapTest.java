package com.beitie.hashmap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/12/17
 */
public class MapTest {
    public static void main(String[] args) {
        int threshold= fetchMapThreshHold();
        System.out.println(threshold);
//        BiConsumer consumer = (key,value) -> {
//            System.out.println(key+"============="+value);
//        };
//
//        map.forEach(consumer);
    }

    /**
     * @author betieforever
     * @description 通过反射获取map的阈值（CAPACITY * LoadFactor 容量*负载因子）
     * 个人理解CAPACITY为数组的长度
     * @date 2021/12/17
     */
    public static int fetchMapThreshHold(){
        HashMap<String,String> map = new HashMap<>();
        map.put("name","zhangbei");
        map.put("sex","girl");
        for (int i = 0 ;i<1200 ; i++){
            map.put("name"+i ,"zhangbei"+i);
        }
        int threshold  = 0;
        try {
            Field field = map.getClass().getDeclaredField("threshold");
            field.setAccessible(true);
            try {
                threshold =field.getInt(map);
                System.out.println("try======"+threshold);
                return threshold;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }finally {
            threshold  = 1;
            System.out.println("finally===="+threshold);
        }
        System.out.println(threshold);
        return 3;
    }

}
