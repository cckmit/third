package com.beitie.base.bean;

import java.io.Serializable;

/**
 * @author betieforever
 * @description 用户信息
 * @date 2021/5/18
 */
public class User implements Serializable {

    private String uid;
    private String name;
    private String address;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(String uid, String name, String address) {
        this.uid = uid;
        this.name = name;
        this.address = address;
    }
}
