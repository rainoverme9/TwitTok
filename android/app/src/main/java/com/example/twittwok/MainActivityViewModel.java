package com.example.twittwok;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<Boolean> checkConncetion;

    public MutableLiveData<Boolean> getCheckConncetion() {
        if (checkConncetion == null) {
            checkConncetion = new MutableLiveData<>();
        }
        return checkConncetion;
    }

    public void setCheckConncetion(Boolean check) {
        checkConncetion.postValue(check);
    }
}
