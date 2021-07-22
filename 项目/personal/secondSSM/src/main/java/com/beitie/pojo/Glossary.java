package com.beitie.pojo;

public class Glossary {
    private int id;
    private String name;
    private int category;
    private short code;
    private byte status;
    private byte displayOrder;

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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(byte displayOrder) {
        this.displayOrder = displayOrder;
    }
}
