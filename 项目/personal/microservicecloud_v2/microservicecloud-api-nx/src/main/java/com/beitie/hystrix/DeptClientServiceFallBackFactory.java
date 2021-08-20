package com.beitie.hystrix;

import com.beitie.entity.Dept;
import com.beitie.service.DeptClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeptClientServiceFallBackFactory implements FallbackFactory<DeptClientService> {

    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public void addDept(Dept dept) {

            }

            @Override
            public void deleteDept(Long id) {

            }

            @Override
            public void updateDept(Dept dept) {

            }

            @Override
            public List<Dept> findAll() {
                return null;
            }

            @Override
            public Dept getDeptById(Long id) {
                return new Dept("该部门不存在或者当前库没有该数据factory", id, "unknown");
            }
        };
    }
}
