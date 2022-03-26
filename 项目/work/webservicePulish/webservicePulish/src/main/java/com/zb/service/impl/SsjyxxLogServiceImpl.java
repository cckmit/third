package com.zb.service.impl;

import com.zb.bean.SsjyxxLog;
import com.zb.mapper.SsjyxxLogMapper;
import com.zb.service.SsjyxxLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("ssjyxxLogService")
public class SsjyxxLogServiceImpl implements SsjyxxLogService {
    @Autowired
    private SsjyxxLogMapper ssjyxxLogMapper;

    @Transactional(isolation= Isolation.READ_COMMITTED,propagation= Propagation.REQUIRED)
    public void insertData(SsjyxxLog jyxxLog) {
        ssjyxxLogMapper.insertData(jyxxLog);
    }
}
