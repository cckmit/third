package com.beitie.springboot_quick001.controller;

import com.beitie.springboot_quick001.entity.User;
import com.beitie.springboot_quick001.service.RedisListService;
import com.beitie.springboot_quick001.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RedisListService redisListService;
    @RequestMapping("/login")
    public String login(String username,String password){
        //1.验证当前用户是否锁定
        Map<String, Object> userLockTime = userService.getUserLockTime(username);

        //1.1如果已经锁定，返回客户端信息，用户已经被锁定
        if((Boolean) userLockTime.get("locked")){
            return "已被限制，还剩余"+(Long)userLockTime.get("timeLock")+"秒";
        }else{
         //1.2如果未被锁定，开始进行登录
            boolean loginFlag=userService.login(username,password);
            if(!loginFlag){
                //2.1 登录失败,则在失败信息的次数上加1
                long failureCount=userService.getLoginFailureCount(username);
                //2.2 若失败的次数超过或者等于规定的次数，则限制用户，并设定期限
                if(failureCount >= User.failureCount){
                    userService.setUserLockTime(username);
                    return "在2分钟内已经登录失败3次，您已被限制登录";
                }else{
                    long remainCount= User.failureCount - failureCount;
                    long remainTime=userService.getUserLockTimeRemain(username);
                    return "登录失败，在"+remainTime+"秒内，还有"+remainCount+"次可以登录，请重新登录";
                }

            }
        }


        //2.2 判断当前的次数是否已经超过标准，
        //2.2.1 若超过，开启锁定当前所用户，记录信息
        //2.2.2 若未超过，返回登陆失败信息，返回登录页面
        //3. 登录成功，返回成功信息。
        return "success";

    }

    @RequestMapping("/execT")
    public String executeTaskTest(){
        return redisListService.executeSingleTask();
    }
    @RequestMapping("/finishedT")
    public List<String> finishedTaskTest(){
        List<String> list=redisListService.finishedTask();
        return list;
    }
    @RequestMapping("/unFinishedT")
    public List<String> unFinishedTaskTest(){
        List<String> list=redisListService.unFinishedTask();
        return list;
    }
    @RequestMapping("/nextT")
    public String nextExecuteTaskTest(){
        String task=redisListService.nextExecuteTask();
        return task;
    }
}
