package com.beitie.service;

import com.beitie.entity.Dept;
import com.beitie.hystrix.DeptClientServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "microservicecloud-dept-provider-consul",fallbackFactory= DeptClientServiceFallBackFactory.class)
public interface DeptClientService {
    @RequestMapping("/dept/add")
    void addDept(Dept dept);
    @RequestMapping("/dept/delte/{id}")
    void deleteDept(@PathVariable("id") Long id);
    @RequestMapping("/dept/update")
    void updateDept(Dept dept);
    @RequestMapping("/dept/list")
    List<Dept> findAll();
    @RequestMapping("/dept/get/{id}")
    Dept getDeptById(@PathVariable("id") Long id);
}
