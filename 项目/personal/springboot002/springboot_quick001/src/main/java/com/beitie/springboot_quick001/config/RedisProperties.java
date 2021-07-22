package com.beitie.springboot_quick001.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author：清风
 * @Description：Redis 基本环境信息
 * @CreateDate：13:48 2018/7/4
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class RedisProperties {

    private String nodes;

    private Integer commandTimeout;

    private Integer maxAttempts;

    private Integer maxRedirects;

    private Integer maxActive;

    private Integer maxWait;

    private Integer maxIdle;

    private Integer minIdle;

    private boolean testOnBorrow;

}