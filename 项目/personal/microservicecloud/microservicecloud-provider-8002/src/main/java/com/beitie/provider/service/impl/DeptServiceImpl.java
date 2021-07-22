package com.beitie.provider.service.impl;

import com.beitie.entity.Dept;
import com.beitie.provider.dao.DeptDao;
import com.beitie.provider.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    public void addDept(Dept dept) {
        deptDao.addDept(dept);
    }

    @Override
    public void deleteDept(Long id) {
        deptDao.deleteDept(id);
    }


    @Override
    public void updateDept(Dept dept) {
        deptDao.updateDept(dept);
    }

    @Override
    public List<Dept> findAll() {
        Dept dept = new Dept();
        
        return deptDao.findAll();
    }

    @Override
    public Dept getDeptById(Long id) {
        return deptDao.getDeptById(id);
    }
}
