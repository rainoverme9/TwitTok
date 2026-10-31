package com.example.twittwok.utils.TwokListUtils;

import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Twok;

import java.util.ArrayList;
import java.util.List;

public class TwokPostRepository {

    private List<Twok> listOfTwoks;

    public TwokPostRepository() {
        this.listOfTwoks = new ArrayList<Twok>();
    }

    public Twok getTwok(int i) { return listOfTwoks.get(i); }

    public void add(Twok twok) { listOfTwoks.add(twok); }

    public  int size() { return listOfTwoks.size(); }


    @Override
    public String toString() {
        return "TwokPostRepository{" +
                "listOfTwoks=" + listOfTwoks +
                '}';
    }
}
