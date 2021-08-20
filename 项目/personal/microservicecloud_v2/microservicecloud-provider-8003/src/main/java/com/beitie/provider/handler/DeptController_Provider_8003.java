package com.beitie.provider.handler;

import com.beitie.entity.Dept;
import com.beitie.provider.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController_Provider_8003 {
    @Autowired
    private DeptService deptService;
    @Autowired
    private DiscoveryClient client;
    @RequestMapping("/list")
    public List<Dept> list(){
        return deptService.findAll();
    }
    @RequestMapping("/get/{id}")
    public Dept get(@PathVariable Long id){
        return deptService.getDeptById(id);
    }
    @RequestMapping("/discover")
    public void discoverMicroServices(){
        List<String> services = client.getServices();
        for (String service : services) {
            System.out.println("service=="+service);
        }
    }

}
