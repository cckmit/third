package com.beitie.custom.handler;

import com.beitie.entity.Dept;
import com.beitie.service.DeptClientService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class DeptController_Custom {
    @Autowired(required = false)
    private DeptClientService deptClientService;
    @RequestMapping("/list")
    public List<Dept>  list(){
        List<Dept> list=deptClientService.findAll();
        return list;
    }
    @RequestMapping("/get/{id}")
    @HystrixCommand(fallbackMethod = "exceptionMethod_get")
    public Dept get(@PathVariable Long  id){
        Dept dept = deptClientService.getDeptById(id);
        if(dept == null){
            try {
                throw new Exception("数据异常");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deptClientService.getDeptById(id);
    }

    public Dept exceptionMethod_get(@PathVariable Long  id){
        return new Dept("该部门不存在或者当前库没有该数据factory", id, "unknown");
    }
}
