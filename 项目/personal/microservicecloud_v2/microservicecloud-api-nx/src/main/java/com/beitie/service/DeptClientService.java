package com.beitie.service;

import com.beitie.entity.Dept;
import com.beitie.hystrix.DeptClientServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Component
@FeignClient(value = "microservicecloud-dept-provider",fallbackFactory= DeptClientServiceFallBackFactory.class)
public interface DeptClientService {
    @RequestMapping("/product/add")
    void addDept(Dept dept);
    @RequestMapping("/product/delte/{id}")
    void deleteDept(@PathVariable("id") Long id);
    @RequestMapping("/product/update")
    void updateDept(Dept dept);
    @RequestMapping("/product/list")
    List<Dept> findAll();
    @RequestMapping("/product/get/{id}")
    Dept getDeptById(@PathVariable("id") Long id);
    @RequestMapping("/product/link")
    String invokeLink();
}
