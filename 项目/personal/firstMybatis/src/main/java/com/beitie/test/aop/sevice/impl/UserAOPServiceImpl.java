package com.beitie.test.aop.sevice.impl;

import com.beitie.test.aop.sevice.UserAOPService;

public class UserAOPServiceImpl implements UserAOPService {
    @Override
    public void add() {
        System.out.println("正在进行添加");
    }

    @Override
    public void delete() {
        System.out.println("正在进行删除");
    }

    @Override
    public String update() {
        System.out.println("正在进行修改");
        return "贝贝";
    }

    @Override
    public void find() {
        System.out.println("正在进行查询");
        int i=1/0;
    }
}
