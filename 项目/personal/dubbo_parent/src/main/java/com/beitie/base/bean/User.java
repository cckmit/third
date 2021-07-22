package com.beitie.base.bean;

/**
 * @author betieforever
 * @description 用户信息
 * @date 2021/5/18
 */
public class User {
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
}
