package com.beitie.springboot_quick001.entity;

import java.io.Serializable;

public class Dept implements Serializable {
    public static final String KEY_NAME="dept";
    private String deptName;
    private Long id;
    private String origDataBase;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigDataBase() {
        return origDataBase;
    }

    public void setOrigDataBase(String origDataBase) {
        this.origDataBase = origDataBase;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptName='" + deptName + '\'' +
                ", id=" + id +
                ", origDataBase='" + origDataBase + '\'' +
                '}';
    }

    public Dept(String deptName, Long id, String origDataBase) {
        this.deptName = deptName;
        this.id = id;
        this.origDataBase = origDataBase;
    }

    public Dept() {
    }
}
