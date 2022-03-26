package com.beitie.hashcode;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/13
 */
public class User {
    private int id;
    private String name;

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

    public static void main(String[] args) {
        User user1=new User();
        System.out.println(user1.hashCode());
        user1.setName("222");
        System.out.println(user1.hashCode());
    }
}
