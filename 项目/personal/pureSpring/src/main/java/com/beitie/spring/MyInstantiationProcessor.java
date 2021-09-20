package com.beitie.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

public class MyInstantiationProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    public MyInstantiationProcessor() {
        super();
        System.out.println("调用MyInstantiationProcessor构造器");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("调用MyInstantiationProcessor的postProcessBeforeInitialization方法");
        return super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("调用MyInstantiationProcessor的postProcessAfterInitialization方法");
        return super.postProcessAfterInitialization(bean, beanName);
    }
}
