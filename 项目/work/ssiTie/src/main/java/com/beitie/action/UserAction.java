package com.beitie.action;

import com.beitie.bean.UserInfo;
import com.beitie.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

/**
 * @description 描述
 * @author betieforever
 * @date 2021/11/21
 */
public class UserAction extends  ActionSupport{
    private List<UserInfo> userList;
    private String name;
    private int flag;
    @Autowired
    private UserDao userDao;
    public String findUserList() throws SQLException {
       userList = userDao.findUserList("T_User_FindUserList");
       name = "张三";
       return ActionSupport.SUCCESS;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
