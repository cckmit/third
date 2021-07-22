package com.beitie.custom.handler;

import com.beitie.entity.Dept;
import com.beitie.service.DeptClientService;
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
    public Dept get(@PathVariable Long  id){
        return deptClientService.getDeptById(id);
    }
}
