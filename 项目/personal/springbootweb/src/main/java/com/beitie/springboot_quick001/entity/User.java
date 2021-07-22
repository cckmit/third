package com.beitie.springboot_quick001.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String name;
    private String age;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static String getUserLockTimeKey(String userName){
        return "user.lockTime."+userName;
    }

    public static String getUserLoginFailureCountKey(String userName){
        return "user.count.failure."+userName;
    }

    public static final long failureCount=3;
    public static final String KEY_NAME="user";
}
