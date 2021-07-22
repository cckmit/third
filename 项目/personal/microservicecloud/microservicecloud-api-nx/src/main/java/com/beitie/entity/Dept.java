package com.beitie.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class Dept implements Serializable {
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
