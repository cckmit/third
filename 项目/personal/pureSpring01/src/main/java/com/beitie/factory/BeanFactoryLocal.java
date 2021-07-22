package com.beitie.factory;

import com.beitie.bean.Music;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

public class BeanFactoryLocal implements ApplicationContextAware {
    public static ApplicationContext applicationContextLocal;
    public static Music createStaticMusic(){
        return new Music();
    }
    public Music createNormalMusic(){
        return new Music();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContextLocal =applicationContext;
    }
}
