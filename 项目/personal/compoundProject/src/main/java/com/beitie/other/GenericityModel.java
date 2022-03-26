package com.beitie.other;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/15
 */
public class GenericityModel<T extends String>{
    private T t;
    public T getObject(){
        return t;
    }

    public static void main(String[] args) {
        Field[] declaredFields = GenericityModel.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getType());
        }
        Method[] declaredMethods = GenericityModel.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            Class a=declaredMethod.getReturnType();
            System.out.println(declaredMethod.getName());
            System.out.println(a.getSimpleName());
        }
    }
}
