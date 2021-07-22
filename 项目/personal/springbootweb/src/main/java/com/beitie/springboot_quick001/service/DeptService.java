package com.beitie.springboot_quick001.service;

import com.beitie.springboot_quick001.entity.Dept;

import java.util.List;

public interface DeptService {
    void addDept(Dept dept);
    void deleteDept(Long id);
    void updateDept(Dept dept);
    List<Dept> findAll();
    Dept getDeptById(Long id);

    void addDept_Redis(Dept dept);
    Dept getDeptById_Redis(Long id);
}
