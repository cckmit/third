package com.beitie.provider.handler;

import com.beitie.entity.Dept;
import com.beitie.provider.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController_Provider_8080 {
    @Autowired
    private DeptService deptService;
    @Autowired
    private DiscoveryClient client;
    @RequestMapping("/list")
    public List<Dept> list(){
        return deptService.findAll();
    }
    @RequestMapping("/get/{id}")
    @HystrixCommand(fallbackMethod="hystrixProcess_get")
    public Dept get(@PathVariable Long id){
        Dept dept=deptService.getDeptById(id);
        if(dept == null){
            throw new RuntimeException("数据不存在");
        }
        return deptService.getDeptById(id);
    }
    @RequestMapping("/discover")
    public void discoverMicroServices(){
        List<String> services = client.getServices();
        for (String service : services) {
            System.out.println("service=="+service);
        }
    }

    public Dept hystrixProcess_get(@PathVariable Long id) {
        return new Dept("该部门不存在或者当前库没有该数据", id, "unknown");
    }
}
