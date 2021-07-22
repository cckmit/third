package com.beitie.provider.dao;

import com.beitie.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DeptDao {
    void addDept(Dept dept);
    void deleteDept(Long id);
    void updateDept(Dept dept);
    List<Dept> findAll();
    Dept getDeptById(Long id);
}
