package com.beitie.bean;

import java.io.Serializable;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/4
 */
public class People implements Serializable {
    private int id;
    private String realName;
    private String IDNumber;
    private int uid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", IDNumber='" + IDNumber + '\'' +
                ", uid=" + uid +
                '}';
    }
}
