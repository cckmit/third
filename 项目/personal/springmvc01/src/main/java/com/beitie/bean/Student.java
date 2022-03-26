package com.beitie.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.concurrent.FutureTask;

public class Student implements Serializable {
    private int id;
    private String name;
    private String address;
    private String sexName;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", sexName='" + sexName + '\'' +
                '}';
    }
}
