package com.example.twittwok.utils.DataBase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StoredUser {
    @PrimaryKey
    @NonNull
    private String uid;

    @NonNull
    private Integer pversion;
    @NonNull
    private String picture;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getPversion() {
        return pversion;
    }

    public void setPversion(Integer pversion) {
        this.pversion = pversion;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "StoredUser{" +
                "uid='" + uid + '\'' +
                ", pversion=" + pversion +
                ", picture='" + picture + '\'' +
                '}';
    }
}
