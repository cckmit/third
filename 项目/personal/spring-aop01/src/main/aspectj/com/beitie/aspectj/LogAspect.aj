package com.beitie.aspectj;

import com.beitie.service.IStudentService;
import com.beitie.aop.aspect.LogManagerV2;
import org.springframework.stereotype.Component;

@Component
public aspect LogAspect {
    pointcut recordStudyLog():call(void IStudentService.study());
    pointcut recordDoLog():execution(* com.beitie.service..do*(..));
    before():recordStudyLog(){
        LogManagerV2 logManagerV2 = new LogManagerV2();
        logManagerV2.prepareWork();
        System.out.println("wife good morning");
    }
    after():recordDoLog(){
        LogManagerV2 logManagerV2 = new LogManagerV2();
        logManagerV2.reviewWork();
        System.out.println("wife good night");
    }
}
