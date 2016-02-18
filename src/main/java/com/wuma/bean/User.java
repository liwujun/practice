package com.wuma.bean;

/**
 * Created by liwujun
 * on 2016/1/4 at 14:05
 */
public class User {
    public User(String id,String name){
        this.id=id;
        this.name=name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String id;
    private String name;

    @Override
    public String toString() {
        return "id=" + id + ",name=" + name;
    }
}
