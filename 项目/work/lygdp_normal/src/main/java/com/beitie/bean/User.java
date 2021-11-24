package com.beitie.lygdp_normal.bean;

import com.beitie.lygdp_normal.util.PhotoEncodeBase64;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private int uid;
    private String uname;
    private int uage;
    private String address;
    private String sex;
    private String grade;
    private Date ubirth;
    private List<Orders> orders;
    private People people;
    private byte[] photo;
    private String photoBase64;

    public String getPhotoBase64() throws IOException {

        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {

    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) throws IOException {
        this.photo = photo;
        if(photo != null && photo.length > 0){
            this.photoBase64 =  "data:image/jpg;base64,"+ PhotoEncodeBase64.toBase64(photo);
        }
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getUage() {
        return uage;
    }

    public void setUage(int uage) {
        this.uage = uage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getUbirth() {
        return ubirth;
    }

    public void setUbirth(Date ubirth) {
        this.ubirth = ubirth;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", uage=" + uage +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", grade='" + grade + '\'' +
                ", ubirth=" + ubirth +
                ", orders=" + orders +
                ", people=" + people +
                '}';
    }
}

