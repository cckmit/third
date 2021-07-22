package com.beitie.base.service;

import com.beitie.base.bean.User;

import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/18
 */
@FunctionalInterface
public interface UserService {
    List<User> findUserByUid(String uid);
    static void findAllUsers(){
        System.out.println("------");
    }
    default void deleteUsers(){

    }
    class UserServiceLocal implements  UserService{
        @Override
        public List<User> findUserByUid(String uid) {
            findAllUsers();
            return null;
        }

        public static void main(String[] args) {
            UserServiceLocal local=new UserServiceLocal();
            local.findUserByUid("");
        }

    }
}
