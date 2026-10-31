package com.example.twittwok.utils.ReuqestUtils.GetRequestClass;

public class User {
    private String name, uid, picture;
    private Integer pversion;

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPicture() {return picture;}

    public void setPicture(String picture) {this.picture = picture;}

    public String getUid() {return uid;}

    public void setUid(String uid) {this.uid = uid;}

    public Integer getPversion() {return pversion;}

    public void setPversion(Integer pversion) {this.pversion = pversion;}

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", uid=" + uid +
                ", pversion=" + pversion +
                '}';
    }
}
