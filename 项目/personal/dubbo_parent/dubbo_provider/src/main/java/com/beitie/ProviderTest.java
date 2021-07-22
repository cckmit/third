package com.beitie;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/22
 */
public class ProviderTest {
    public static void main(String[] args) throws Exception {
        init();
    }
    public static void init() throws Exception {
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
}
