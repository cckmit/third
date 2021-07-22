package com.beitie.handler;

import com.beitie.bean.User;
import com.beitie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping("/findAllUsers")
    public String findAllUsers(Model model){
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("userList",allUsers);
        return "userInfo";
    }
    @RequestMapping("/getUserInfoById")
    public ModelAndView getUserInfoById(@RequestParam(value = "uid",defaultValue = "1") int id){
        User user = userService.findUserInfoById(1);
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("userinfo",user);
        modelAndView.setViewName("singleUserInfo");
        return modelAndView;
    }

}
