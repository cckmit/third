package com.beitie.springboot_quick001.service;

import com.beitie.springboot_quick001.SpringbootQuick001Application;
import com.beitie.springboot_quick001.entity.Dept;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootQuick001Application.class)
public class DeptServiceTest {
    @Resource
    private DeptService deptService;
    @Test
    public void findAllTest(){
        List<Dept> list=deptService.findAll();
        System.out.println(list);
    }
    @Test
    public void getDeptByIdTest(){
        deptService.getDeptById_Redis(2l);
    }
}
