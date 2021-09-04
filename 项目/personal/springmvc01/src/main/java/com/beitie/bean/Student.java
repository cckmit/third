package com.beitie.bean;

import java.util.Collections;
import java.util.concurrent.FutureTask;

public class Student {
    private int id;
    private String name;
    private String address;
    private String sexName;

    public Student(String name, String address, String sexName) {
        FutureTask
        this.name = name;
        this.address = address;
        this.sexName = sexName;
    }

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
