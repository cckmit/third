package com.beitie.hanlder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/25
 */
@RestController
public class HystrixHandler {

    @RequestMapping("/defaultFallback")
    public Map<String, String> defaultfallback() {
        System.out.println("降级操作...");
        Map<String, String> map = new HashMap<>();
        map.put("resultCode", "fail");
        map.put("resultMessage", "服务异常");
        map.put("resultObj", "null");
        return map;
    }
}
