package com.beitie.springboot_quick001.service.impl;

import com.beitie.springboot_quick001.service.TestService;

public class TestServiceImpl implements TestService {
    @Override
    public void test() {
        System.out.println("------正在测试------");
    }
}
