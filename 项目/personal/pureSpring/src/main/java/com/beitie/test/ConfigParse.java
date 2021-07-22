package com.beitie.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigParse {
    static Properties properties ;
    static Map<String,String> cachedMap = new HashMap<String, String>();
    static {
        try {
            properties = new Properties();
            properties.load(ConfigParse.class.getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new ExceptionInInitializerError("初始化失败！！！");
        }
    }


    public static String getString(String key){
        String value=cachedMap.get(key);
        if(cachedMap.containsKey(key)){
            return cachedMap.get(key);
        }
        return properties.getProperty(key);
    }
}
