package com.beitie.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/25
 */
@Configuration
public class ConfigCenter {
    @PostConstruct
    public void init() throws Exception {
        CuratorFramework zkClient = CuratorFrameworkFactory.builder().
                connectString("127.0.0.1:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        zkClient.start();

        if (zkClient.checkExists().forPath("/dubbo/config/dubbo/dubbo.properties") == null) {
            zkClient.create().creatingParentsIfNeeded().forPath("/dubbo/config/dubbo/dubbo.properties");
        }
        zkClient.setData().forPath("/dubbo/config/dubbo/dubbo.properties", ("dubbo.registry.address=zookeeper://127.0.0.1:2181\n" +
                "dubbo.metadata-report.address=zookeeper://127.0.0.1:2181").getBytes());
    }
//我这是第二个注册中心，所以是2181 这个端口号需要根据你的注册中心修改
}
