package com.example.twittwok.ui.follower;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twittwok.utils.FollowerUtils.FollowerListRepository;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Twok;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.User;

public class FollowerListViewModel extends ViewModel {

    private MutableLiveData<FollowerListRepository> listOfFollower;
    private boolean error;

    public boolean getError() { return error; }

    public MutableLiveData<FollowerListRepository> getListOfFollower() {
        if (listOfFollower == null) {
            listOfFollower = new MutableLiveData<FollowerListRepository>(new FollowerListRepository());
        }
        return listOfFollower;
    }

    public int size() {
        return listOfFollower.getValue().size();
    }

    public void getFollowed() {
        CommunicationController.getFollowed(
                response -> {
                    for (User user: response ) {
                        this.listOfFollower.getValue().add((User) user);
                        this.listOfFollower.setValue(listOfFollower.getValue());
                    }
                },
                error -> this.error = true
        );
    }

}
