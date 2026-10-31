package com.example.twittwok.utils.ReuqestUtils.InterfaceRequestClass;

import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.User;

import java.util.List;

public interface OnFollowerOk {
    void onResponseComplete(List<User> listOfFollower);
}
