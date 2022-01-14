package com.zb.service;

import com.zb.ws.po.fetchBizInfo.Jyxx;

import java.util.List;
import java.util.Map;

public interface SsjyxxService {
    List<Jyxx> fetchListedBizInfo(Map<String,Object> map);
}
