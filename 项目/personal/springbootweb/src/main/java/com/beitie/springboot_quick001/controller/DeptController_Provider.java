package com.beitie.springboot_quick001.controller;

import com.beitie.springboot_quick001.entity.Dept;
import com.beitie.springboot_quick001.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController_Provider {
    @Autowired
    private DeptService deptService;
    @RequestMapping("/list")
    public List<Dept> list(){
        return deptService.findAll();
    }
    @RequestMapping("/get/{id}")
    public Dept get(@PathVariable Long id){
        return deptService.getDeptById(id);
    }

}
