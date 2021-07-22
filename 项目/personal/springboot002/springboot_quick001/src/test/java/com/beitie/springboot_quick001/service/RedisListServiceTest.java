package com.beitie.springboot_quick001.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisListServiceTest {
    @Autowired
    private RedisListService redisListService;
    @Test
    public void listAddTest(){
        List<String> list= new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        redisListService.listAdd(list);
    }
    @Test
    public void listTest(){
        List<String> list = redisListService.list();
        for (String s : list) {
            System.out.println(s);
        }
    }
    @Test
    public void list_page(){
        List<String> list=redisListService.list_page(1);
        System.out.println(list);
    }
    @Test
    public void initListTest(){
        List<String> list=new ArrayList<>();
        list.add("第一步");
        list.add("第2步");
        list.add("第3步");
        list.add("第4步");
        list.add("第5步");
        list.add("第6步");
        redisListService.initSourceList(list);
    }
    @Test
    public void executeTaskTest(){
        redisListService.executeSingleTask();
    }
    @Test
    public void finishedTaskTest(){
        List<String> list=redisListService.finishedTask();
        System.out.println(list.toString());
    }
    @Test
    public void unFinishedTaskTest(){
        List<String> list=redisListService.unFinishedTask();
        System.out.println(list.toString());
    }
    @Test
    public void nextExecuteTaskTest(){
        String task=redisListService.nextExecuteTask();
        System.out.println(task);
    }

}
