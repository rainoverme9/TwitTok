package com.example.twittwok.ui.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Twok;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.TwokListUtils.TwokPostRepository;

public class ProfileTwokListViewModel extends ViewModel {
    private MutableLiveData<TwokPostRepository> listOfTwoks;
    private boolean error;

    public MutableLiveData<TwokPostRepository> getListOfTwoks() {
        if (listOfTwoks == null) {
            listOfTwoks = new MutableLiveData<TwokPostRepository>(new TwokPostRepository());
        }
        return listOfTwoks;
    }

    public boolean getError() { return error; }

    public int size() {
        return listOfTwoks.getValue().size();
    }


    public void downloadOneMoreTwok(String uid) {
        CommunicationController.getTwok(
                uid, null,
                response -> {
                    this.listOfTwoks.getValue().add((Twok) response);
                    this.listOfTwoks.setValue(listOfTwoks.getValue());
                },
                error    -> { this.error = true; }
        );
    }
}
