package com.beitie.other;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @description 描述
 * @author betieforever
 * @date 2021/10/14
 */   public class StringModel {
    public static void main(String[] args) {
        String str="java1 Java2 lll Java3 JAva4 ljsldjfljava5";
        str=str.toLowerCase();
        boolean flag = true;
        int i=1;
        Map<Integer,String> map = new HashMap<>();
        while (flag){
            //此处为大写的原因是下面要替换的是小写的java，如果不是替换后还是小写的，会影响后续操作
                String repStr="JAVA"+i;
                map.put(i,repStr);
                if(str.contains("java")){
                    //因为要一个一个的标记要替换的java，每个待替换的都是不一样的，比如依次为JAVA1,JAVA2,JAVA3
                    str=str.replaceFirst("java",repStr);
                    i++;
                }else{
                    flag=false;
                }

        }
        System.out.println(str);
        for (Integer a : map.keySet()) {
            if(a%2==1){
                str=str.replace(map.get(a),"java");
            }else{
                str=str.replace(map.get(a),"JAVA");
            }
        }
        System.out.println(str);
    }
}
