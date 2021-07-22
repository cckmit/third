package com.beitie.provider.service;

import com.beitie.entity.Dept;

import java.util.List;

public interface DeptService {
    void addDept(Dept dept);
    void deleteDept(Long id);
    void updateDept(Dept dept);
    List<Dept> findAll();
    Dept getDeptById(Long id);
}
