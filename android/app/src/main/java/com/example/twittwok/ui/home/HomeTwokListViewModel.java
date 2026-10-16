package com.example.twittwok.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Twok;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.TwokListUtils.TwokPostRepository;

public class HomeTwokListViewModel extends ViewModel {
    private MutableLiveData<TwokPostRepository> listOfTwoks;
    private final MutableLiveData<Integer> tidSequence = new MutableLiveData<>(-1);
    private boolean error;

    public MutableLiveData<TwokPostRepository> getListOfTwoks() {
        if (listOfTwoks == null) {
            listOfTwoks = new MutableLiveData<>(new TwokPostRepository());
        }
        return listOfTwoks;
    }

    public boolean getError() { return error; }

    public int size() {
        return listOfTwoks.getValue().size();
    }


    public void downloadOneMoreTwok() {
        if (tidSequence.getValue() == -1) {
            CommunicationController.getTwok(
                    null, null,
                    response -> {
                        this.listOfTwoks.getValue().add((Twok) response);
                        this.listOfTwoks.setValue(listOfTwoks.getValue());
                        this.error = false;
                    },
                    error    -> this.error = true
            );
        } else {
            CommunicationController.getTwok(
                    null, String.valueOf(tidSequence.getValue()),
                    response -> {
                        this.listOfTwoks.getValue().add((Twok) response);
                        this.listOfTwoks.setValue(listOfTwoks.getValue());
                        this.error = false;
                    },
                    error    -> this.error = true
            );
            tidSequence.setValue(tidSequence.getValue()+1);
        }
    }
}
