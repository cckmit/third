package com.beitie.pojo;

import com.beitie.util.CodeMapUtils;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private int id;
    private String name;
    private int age;
    private String address;
    private byte sex;
    private byte grade;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public byte getGrade() {
        return grade;
    }

    public void setGrade(byte grade) {
        this.grade = grade;
    }

    public String getSexName(){
        return CodeMapUtils.getNameByCategoryAndCode(101,getSex());
    }
    public String getGradeName(){
        return CodeMapUtils.getNameByCategoryAndCode(102,getGrade());
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", sex=" + sex +
                ", grade=" + grade +
                '}';
    }
}
