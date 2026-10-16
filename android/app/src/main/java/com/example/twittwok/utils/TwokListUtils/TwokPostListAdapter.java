package com.example.twittwok.utils.TwokListUtils;

import static androidx.core.content.ContextCompat.getMainExecutor;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.twittwok.R;
import com.example.twittwok.utils.DataBase.StoredUser;
import com.example.twittwok.utils.DataBase.StoredUserRepository;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Picture;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Twok;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TwokPostListAdapter extends RecyclerView.Adapter<TwokPostListHolder>  {


    private LayoutInflater inflater;
    private TwokPostRepository listOfTwoks;
    private boolean actionToUserInfo;

    private  Context context;

    public TwokPostListAdapter(TwokPostRepository listOfTwoks, Context context, boolean actionToUserInfo) {
        //ListOfTwoks
        this.listOfTwoks = listOfTwoks;
        //Layout
        this.inflater = LayoutInflater.from(context);
        //Action to UserInfo
        this.actionToUserInfo = actionToUserInfo;
        //View
        this.context = context;
    }

    @NonNull
    @Override   //Crea una cella della lista
    public TwokPostListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listoftwoks, parent, false);
        return new TwokPostListHolder(view);
    }

    @Override   //Associa una cella della lista al contenuto
    public void onBindViewHolder(@NonNull TwokPostListHolder twokPostListHolder, int position) {
        Twok twokToShow = listOfTwoks.getTwok(position);

        StoredUserRepository db = Room.databaseBuilder(context, StoredUserRepository.class, "database").build();
        ListenableFuture<StoredUser> storedUser = db.storedUserDao().getUser(twokToShow.getUid());

        if (twokToShow.getPversion() > 0) {
            storedUser.addListener(() -> {
                try {
                    //Seleziono l'utente
                    StoredUser user = storedUser.get();

                    //Se l'utente è presente
                    if (user != null) {
                        listOfTwoks.getTwok(position).setPicture(user.getPicture());
                        twokPostListHolder.updateContent(twokToShow, actionToUserInfo);


                        //Se la versione della foto è stata aggiornata
                        //1. scarico la foto dal server e la inserisco nel db
                        //2. aggiorno la foto nell'apapter
                        if(twokToShow.getPversion() > user.getPversion()) {
                            CommunicationController.getPicture(
                                    String.valueOf(twokToShow.getUid()),
                                    response -> {
                                        listOfTwoks.getTwok(position).setPicture(((Picture) response).getPicture());
                                        twokPostListHolder.updateContent(twokToShow, actionToUserInfo);
                                        StoredUser newStoredUser = new StoredUser();
                                        newStoredUser.setUid(twokToShow.getUid());
                                        newStoredUser.setPversion(twokToShow.getPversion());
                                        newStoredUser.setPicture(twokToShow.getPicture());
                                        db.storedUserDao().insertAll(newStoredUser);
                                    },
                                    error -> {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("Somenting went wrong");}
                            );
                        }

                    //se l'utente non è presente nel database
                    } else {
                        //1. scarico la foto dal server e la inserisco nel db
                        //2. aggiorno la foto nell'apapter
                        CommunicationController.getPicture(
                                String.valueOf(twokToShow.getUid()),
                                response -> {
                                    listOfTwoks.getTwok(position).setPicture(((Picture) response).getPicture());
                                    twokPostListHolder.updateContent(twokToShow, actionToUserInfo);
                                    StoredUser newStoredUser = new StoredUser();
                                    newStoredUser.setUid(String.valueOf(twokToShow.getUid()));
                                    newStoredUser.setPversion(twokToShow.getPversion());
                                    newStoredUser.setPicture(twokToShow.getPicture());
                                    db.storedUserDao().insertAll(newStoredUser);
                                },
                                error -> {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("Somenting went wrong");}
                        );
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, getMainExecutor(context));
        }

        twokPostListHolder.updateContent(twokToShow, actionToUserInfo);
    }

    @Override   //Bisogna restituire il numero di elementi della lista
    public int getItemCount() {
        return listOfTwoks.size();
    }
}
