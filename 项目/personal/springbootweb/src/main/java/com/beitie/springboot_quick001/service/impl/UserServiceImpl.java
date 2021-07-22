package com.beitie.springboot_quick001.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.beitie.springboot_quick001.entity.User;
import com.beitie.springboot_quick001.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource(name="redisTemplate")
    private HashOperations<String,Integer,User> hashOperations;
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }
    @Override
    public Map<String, Object> getUserLockTime(String username) {
        String lockKey= User.getUserLockTimeKey(username);
        Map<String, Object> map=new HashMap<>();
        boolean flag=redisTemplate.hasKey(lockKey);
        Long minutes=0l;
        if(flag){
            minutes=redisTemplate.getExpire(lockKey, TimeUnit.SECONDS);
        }
        map.put("locked",flag);
        map.put("timeLock",minutes);
        return map;
    }

    @Override
    public long getLoginFailureCount(String username) {
        String loginFailureKey=User.getUserLoginFailureCountKey(username);
        long failureCount=redisTemplate.boundValueOps(loginFailureKey).increment();
        redisTemplate.boundValueOps(loginFailureKey).expire(1,TimeUnit.MINUTES);
        return failureCount;
    }

    @Override
    public boolean login(String userName, String password) {
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            return false;
        }
        if(userName.equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public void setUserLockTime(String username) {
        String lockKey= User.getUserLockTimeKey(username);
        redisTemplate.boundValueOps(lockKey).set("限制",2,TimeUnit.MINUTES);
    }

    @Override
    public long getUserLockTimeRemain(String username) {
        String lockKey= User.getUserLoginFailureCountKey(username);
        Long timeRemain=redisTemplate.getExpire(lockKey,TimeUnit.SECONDS);
        return timeRemain;
    }

    @Override
    public void addUser(User user) {
        hashOperations.put(User.KEY_NAME,user.getId(),user);
    }

    @Override
    public void getUserById(int id) {

    }
}
