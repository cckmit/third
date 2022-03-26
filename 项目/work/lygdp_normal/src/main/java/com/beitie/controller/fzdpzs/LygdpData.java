package com.beitie.controller.fzdpzs;

import com.beitie.mapper.YjxxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/13
 */
@RestController
@RequestMapping("/fzdpzs")
public class LygdpData {
    @Autowired
    YjxxMapper yjxxMapper;
    @RequestMapping("/getDataCount")
    public Object getDataCount(String keyword){
        Map map  = new HashMap();
        map.put("data","1");
        return map;
    }

    @RequestMapping("/getData")
    public Object getDatat(String keyword){
        Map map  = new HashMap();
        map.put("data",yjxxMapper.findYjxx().subList(1,9));
        return map;
    }
}
