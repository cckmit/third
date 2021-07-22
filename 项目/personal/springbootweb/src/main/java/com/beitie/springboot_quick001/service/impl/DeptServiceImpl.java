package com.beitie.springboot_quick001.service.impl;

import com.beitie.springboot_quick001.dao.DeptDao;
import com.beitie.springboot_quick001.entity.Dept;
import com.beitie.springboot_quick001.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;
    private RedisTemplate redisTemplate;
    private HashOperations<String,Long, Dept> hashOperations;
    private ListOperations listOperations;
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
        this.hashOperations=redisTemplate.opsForHash();
        this.listOperations=redisTemplate.opsForList();
    }
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

    @Override
    public void addDept_Redis(Dept dept) {
        hashOperations.put(dept.KEY_NAME,dept.getId(),dept);
    }

    @Override
    public Dept getDeptById_Redis(Long id) {
        Dept dept;
        if(hashOperations.hasKey(Dept.KEY_NAME,id)){
            dept=hashOperations.get(Dept.KEY_NAME,id);
            System.out.println("从redis中取数据");
        }else{
            dept=getDeptById(id);
            System.out.println("从mysql中取数据");
            addDept_Redis(dept);
        }
        return dept;
    }

}
