package com.beitie.springboot_quick001.dao;

import com.beitie.springboot_quick001.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DeptDao {
    void addDept(Dept dept);
    void deleteDept(Long id);
    void updateDept(Dept dept);
    List<Dept> findAll();
    Dept getDeptById(Long id);
}
