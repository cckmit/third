package com.beitie.controller;

import com.beitie.bean.User;
import com.beitie.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/13
 */
@Controller
@RequestMapping("/view")
public class PhoteViewController {
    @Resource
    UserMapper userMapper;
    @RequestMapping("/{id}")
    public void viewPictureById(@PathVariable(name = "id")int id, HttpServletResponse response){
       User user = userMapper.selectUserById(id);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(user.getPhoto());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
