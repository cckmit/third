package com.beitie.lygdp_normal.controller;

import com.beitie.lygdp_normal.bean.User;
import com.beitie.lygdp_normal.service.UserService;
import com.beitie.lygdp_normal.util.PhotoEncodeBase64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserInfoHandler3 {
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
    @RequestMapping("/queryUserListInfoAndOrders")
    public ModelAndView queryUserListInfoAndOrders(Integer a) {
        logger.debug("debug message");
        List<User> list=userService.selectAllUserAndOrders();
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

    @RequestMapping("/findUsersByRelation")
    public ModelAndView findUsersByRelation() {
        logger.debug("debug message");
        List<User> list=userService.findUsersByRelation();
        for (User user : list) {
            System.out.println(user.getOrders());
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("userInfoList",list);
        modelAndView.setViewName("userInfo");
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
    public String updateUserInfo(@Valid User user, BindingResult bindingResult, Model model,String s) throws IOException {
        if (bindingResult.hasErrors()){
            List<ObjectError> list=bindingResult.getAllErrors();
            for (ObjectError objectError : list) {
                System.out.println(objectError.getDefaultMessage());
            }
            return "UpdateUserInfo";
        }
//        InputStream in=new FileInputStream(new File("d:/1634912990.png"));
//        byte [] b = new byte[in.available()];
//        in.read(b);
        user.setPhoto(PhotoEncodeBase64.covertToByteArray(s));
        userService.updateUser(user);
        return "redirect:queryUserListInfo.action";
    }
    @RequestMapping("/addUser")
    public String addUser(User user) throws IOException {



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
    public String updateUsersForDeleteUsers(Integer[] ids){
        for (Integer id : ids) {
            userService.updateUsersForDeleteUsers(id);
        }
        return "redirect:queryUserListInfo.action?a=1";
    }
    @InitBinder
    public void init(WebDataBinder webDataBinder){
        //指定什么格式，前台传什么格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class,new CustomDateEditor(simpleDateFormat,false));
    }

    @RequestMapping(value="viewDirect")
    public void viewDirect(HttpServletResponse response) throws Exception {
        File file = new File("d:/1634909675.jpg");
        if (file.exists()&&file.isFile()) {
            InputStream inputStream = new FileInputStream(file);
            ServletOutputStream outputStream = response.getOutputStream();
            System.out.println("file--length--");
            byte[] bytes = new byte[(int) file.length()];

            int i=inputStream.read(bytes);
            while (i>0){
                outputStream.write(bytes);
                i=inputStream.read(bytes);
            }
            outputStream.write(bytes);
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        }
    }

    @RequestMapping(value = "/queryCompanyImage", method = RequestMethod.GET)
    @ResponseBody
    public void queryCompanyImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            InputStream fileOssRecordInputStream = null;

            fileOssRecordInputStream = new FileInputStream("d:/1634912990.png");//这是一个阿里云OSS下载图片得到InputStream的方法

            BufferedInputStream reader = new BufferedInputStream(fileOssRecordInputStream);
            BufferedOutputStream writer = new BufferedOutputStream(response.getOutputStream());

            byte[] bytes = new byte[1024 * 1024];
            int length = reader.read(bytes);
            while ((length > 0)) {
                writer.write(bytes, 0, length);
                length = reader.read(bytes);
            }
            reader.close();
            writer.close();

        } catch (Exception ex) {

        }

    }
}
