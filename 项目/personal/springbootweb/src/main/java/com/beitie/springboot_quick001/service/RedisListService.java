package com.beitie.springboot_quick001.service;

import java.util.List;

public interface RedisListService {
    void listAdd(List<String> listData);
    List<String> list();
    List<String> list_page(int pageNum);
    //初始化源数据
    public void initSourceList(List<String> listSource);

    // 执行一步任务
    public String executeSingleTask();

    //查询已执行任务
    public List<String> finishedTask();
    //查询未执行的任务
    public List<String> unFinishedTask();

    //查询下一步将要执行的任务
    public String nextExecuteTask();
}
