package com.example.twittwok.utils.ReuqestUtils.GetRequestClass;

public class Profile {
    private String sid, name, uid, picture;
    private Integer pversion;

    public Profile(String sid, String name, String uid, String picture, Integer pversion) {
        this.sid = sid;
        this.name = name;
        this.picture = picture;
        this.uid = uid;
        this.pversion = pversion;
    }


    public String getSid() {return sid;}

    public void setSid(String sid) {this.sid = sid;}

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
        return "Profile{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", uid=" + uid +
                ", pversion=" + pversion +
                '}';
    }
}
