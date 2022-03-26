package com.zb.bean;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private short age;
    private byte grade;
    private byte sex;
    private String address;

    public User() {
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

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public byte getGrade() {
        return grade;
    }

    public void setGrade(byte grade) {
        this.grade = grade;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", grade=" + grade +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                '}';
    }
}
