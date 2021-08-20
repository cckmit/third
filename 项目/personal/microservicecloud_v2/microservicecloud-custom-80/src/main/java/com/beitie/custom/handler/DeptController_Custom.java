package com.beitie.custom.handler;

import com.beitie.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class DeptController_Custom {
//    public static  final String PREFIX="http://localhost:8002/dept/";
    public static  final String PREFIX="http://MICROSERVICECLOUD-DEPT-PROVIDER/dept/";
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/list")
    public List<Dept>  list(){
        List<Dept> list=restTemplate.getForObject(PREFIX+"list",List.class);
        return list;
    }
}
