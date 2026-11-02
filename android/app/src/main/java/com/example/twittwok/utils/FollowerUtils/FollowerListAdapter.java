package com.example.twittwok.utils.FollowerUtils;

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
import com.example.twittwok.utils.OnListClickEventListener;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Picture;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.User;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FollowerListAdapter extends  RecyclerView.Adapter<FollowerListHolder> {


    private LayoutInflater inflater;
    private FollowerListRepository listOfFollower;
    private Context context;

    public FollowerListAdapter(FollowerListRepository listOfFollower, Context context) {
        this.context = context;

        this.listOfFollower = listOfFollower;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FollowerListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.userinfo, parent, false);
        return new FollowerListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerListHolder followerListHolder, int position) {
        User followerToShow = listOfFollower.getUser(position);

        StoredUserRepository db = Room.databaseBuilder(context, StoredUserRepository.class, "database").build();
        ListenableFuture<StoredUser> storedUser = db.storedUserDao().getUser(followerToShow.getUid());

        if (followerToShow.getPversion() > 0) {
            storedUser.addListener(() -> {
                try {
                    //Seleziono l'utente
                    StoredUser user = storedUser.get();

                    //Se l'utente è presente
                    if (user != null) {
                        listOfFollower.getUser(position).setPicture(user.getPicture());
                        followerListHolder.updateContent(followerToShow);


                        //Se la versione della foto è stata aggiornata
                        //1. scarico la foto dal server e la inserisco nel db
                        //2. aggiorno la foto nell'apapter
                        if(followerToShow.getPversion() > user.getPversion()) {
                            CommunicationController.getPicture(
                                    String.valueOf(followerToShow.getUid()),
                                    response -> {
                                        listOfFollower.getUser(position).setPicture(((Picture) response).getPicture());
                                        followerListHolder.updateContent(followerToShow);

                                        StoredUser newStoredUser = new StoredUser();
                                        newStoredUser.setUid(followerToShow.getUid());
                                        newStoredUser.setPversion(followerToShow.getPversion());
                                        newStoredUser.setPicture(followerToShow.getPicture());
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
                                String.valueOf(followerToShow.getUid()),
                                response -> {
                                    listOfFollower.getUser(position).setPicture(((Picture) response).getPicture());
                                    followerListHolder.updateContent(followerToShow);

                                    StoredUser newStoredUser = new StoredUser();
                                    newStoredUser.setUid(followerToShow.getUid());
                                    newStoredUser.setPversion(followerToShow.getPversion());
                                    newStoredUser.setPicture(followerToShow.getPicture());
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

        followerListHolder.updateContent(followerToShow);
    }

    @Override   //Bisogna restituire il numero di elementi della lista
    public int getItemCount() {
        return listOfFollower.size();
    }
}
