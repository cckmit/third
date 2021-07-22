package com.beitie.springboot_quick001.service;

import com.beitie.springboot_quick001.SpringbootQuick001Application;
import com.beitie.springboot_quick001.entity.Dept;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootQuick001Application.class)
public class RedisTest {
    @Resource
    @Qualifier("redisTemplate")
    private RedisTemplate template;
    @Resource
    private DeptService deptService;
    @Test
    public void read(){
        // 1、获取值
        String s = (String)template.boundValueOps("dept.findAll").get();
        template.expire("dept.findAll",22, TimeUnit.DAYS);
        // 2、判断值是否存在
        if(null == s){
            List<Dept> all = deptService.findAll();
            ObjectMapper objectMapper =  new ObjectMapper();
            try {
                s = objectMapper.writeValueAsString(all);
                template.boundValueOps("dept.findAll").set(s);
                template.boundHashOps("").expire(Duration.ofDays(1));
                template.hasKey("");
                template.boundValueOps("").increment();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        System.out.println(s);


    }
}
