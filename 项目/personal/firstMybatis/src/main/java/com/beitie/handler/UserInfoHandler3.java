package com.beitie.handler;

import com.beitie.bean.User;
import com.beitie.service.UserService;
import com.beitie.util.exception.BusinessRuntimeException;
import com.beitie.util.exception.CustomException;
import com.beitie.util.exception.ResultCode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.transform.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping(value = "/user")
public class UserInfoHandler3  {
        static Logger logger = LogManager.getLogger(UserInfoHandler3.class);
    @Autowired
    private UserService userService;
    @RequestMapping("/queryUserListInfo")
    public ModelAndView queryUserListInfo(Integer a) {
        logger.debug("debug message");
        List<User> list=userService.selectAllUser();
        for (User user : list) {
            System.out.println(user.getOrders());
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("userInfoList",list);
        modelAndView.setViewName("userInfo");
        if(a!=null){
            modelAndView.setViewName("BatchHandleUserInfo");
        }
        return modelAndView;
    }
    @RequestMapping("/querySingleUserInfo")
    public ModelAndView querySingleUserInfo(Integer uid) throws Exception{
        User user=userService.selectUserById(uid);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("user",user);        
        modelAndView.setViewName("UpdateUserInfo");
        return modelAndView;
    }
    @RequestMapping("/querySingleUserInfoInJson")
    @ResponseBody
    public User querySingleUserInfoInJson(Integer uid) throws Exception{
        User user=userService.selectUserById(uid);
        return user;
    }
    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(@Valid User user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            List<ObjectError> list=bindingResult.getAllErrors();
            for (ObjectError objectError : list) {
                System.out.println(objectError.getDefaultMessage());
            }
            return "UpdateUserInfo";
        }
        userService.updateUser(user);
        return "redirect:queryUserListInfo.action";
    }
    @RequestMapping("/addUser")
    public String addUser(User user){
        userService.addUser(user);
        return "redirect:queryUserListInfo.action";
    }
    @RequestMapping("/deleteUserInfo")
    public String deleteUserInfo(Integer uid){
        userService.deleteUser(uid);
        return "redirect:queryUserListInfo.action";
    }
    @RequestMapping("/tiaoZhuan")
    public String turnInto(String viewName) {
        return viewName;
    }

    @RequestMapping("/updateUsersForDeleteUsers")
    public String updateUsersForDeleteUsers(@RequestParam(value="ids",required = true)@Required Integer[] ids){
        for (Integer id : ids) {
            userService.updateUsersForDeleteUsers(id);
        }
        return "redirect:queryUserListInfo.action?a=1";
    }

}
