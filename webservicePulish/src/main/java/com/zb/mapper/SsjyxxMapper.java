package com.zb.mapper;

import com.zb.ws.po.fetchBizInfo.Jyxx;

import java.util.List;
import java.util.Map;
public interface SsjyxxMapper {
    List<Jyxx> fetchListedBizInfo(Map<String,Object> map);
}
