package com.zb.service.impl;

import com.zb.mapper.SsjyxxMapper;
import com.zb.service.SsjyxxService;
import com.zb.ws.po.fetchBizInfo.Jyxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ssjyxxService")
public class SsjyxxServiceImpl implements SsjyxxService {
    @Autowired
    private SsjyxxMapper ssjyxxMapper;
    @Override
    public List<Jyxx> fetchListedBizInfo(Map<String,Object> map) {
        return ssjyxxMapper.fetchListedBizInfo(map);
    }

    public SsjyxxMapper getSsjyxxMapper() {
        return ssjyxxMapper;
    }

    public void setSsjyxxMapper(SsjyxxMapper ssjyxxMapper) {
        this.ssjyxxMapper = ssjyxxMapper;
    }
}
