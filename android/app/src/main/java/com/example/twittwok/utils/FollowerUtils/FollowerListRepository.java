package com.example.twittwok.utils.FollowerUtils;

import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.User;

import java.util.ArrayList;
import java.util.List;

public class FollowerListRepository {
    private List<User> listOfUsers;

    public FollowerListRepository() {
        this.listOfUsers = new ArrayList<User>();
    }

    public User getUser(int i) { return listOfUsers.get(i); }

    public void add(User user) { listOfUsers.add(user); }

    public int size() { return listOfUsers.size(); }
}
