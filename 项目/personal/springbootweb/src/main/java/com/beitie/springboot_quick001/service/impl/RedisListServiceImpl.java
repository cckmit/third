package com.beitie.springboot_quick001.service.impl;

import com.beitie.springboot_quick001.service.RedisListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RedisListServiceImpl implements RedisListService {
    public static final String KEY_NAME="LIST_KEY";
    public static final String KEY_NAME_SOURCE="KEY_SOURCE";
    public static final String KEY_NAME_DESTINATION="KEY_DESTINATION";
    private ListOperations<String,String> list;
    private int pageNum=1;
    private int pageSize=3;

    @Autowired
    public void setRestTemplate(RedisTemplate redisTemplate){
        list=redisTemplate.opsForList();
    }



    @Override
    public void listAdd(List<String> listData) {
        list.rightPushAll(RedisListServiceImpl.KEY_NAME,listData);
    }

    @Override
    public List<String> list() {
        return list.range(KEY_NAME,0,-1);
    }

    public List<String> list_page(int pageNum){
        this.pageNum=pageNum;
        return list.range(KEY_NAME,getPageStart(),getPageEnd());
    }

    public int getPageStart(){
        return (pageNum-1)*pageSize;
    }

    public int getPageEnd(){
        return pageNum*pageSize -1;
    }


    //消息队列
    public void taskList(){

    }

    //初始化源数据
    public void initSourceList(List<String> listSource){
        list.leftPushAll(KEY_NAME_SOURCE,listSource);
    }

    // 执行一步任务
    public String executeSingleTask(){
        String s=list.rightPopAndLeftPush(KEY_NAME_SOURCE,KEY_NAME_DESTINATION);
        return s;
    }

    //查询已执行任务
    public List<String> finishedTask(){
        List<String> listFinished=list.range(KEY_NAME_DESTINATION,0,-1);
        return listFinished;
    }
    //查询未执行的任务
    public List<String> unFinishedTask(){
        return list.range(KEY_NAME_SOURCE,0,-1);
    }

    //查询下一步将要执行的任务
    public String nextExecuteTask(){
        return list.index(KEY_NAME_SOURCE,list.size(KEY_NAME_SOURCE)-1);
    }
}
