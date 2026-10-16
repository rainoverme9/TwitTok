package com.example.twittwok.ui.follower;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.twittwok.R;
import com.example.twittwok.utils.FollowerUtils.FollowerListAdapter;
import com.example.twittwok.utils.FollowerUtils.FollowerListRepository;


public class ListOfFollower {

    static FollowerListAdapter followerListAdapter ;

    public static void getFollower(View root, ViewGroup container, FollowerListViewModel model, LifecycleOwner lifecycleOwner) {

        //Seleziono i componenti del layout
        RecyclerView viewPager = root.findViewById(R.id.viewPager);
        viewPager.setLayoutManager(new LinearLayoutManager(container.getContext()));
        LinearLayout loading = root.findViewById(R.id.loading);
        TextView nessunFollower = root.findViewById(R.id.nessunFollower);

        //2) creo la lista degli utenti seguiti
        model.getListOfFollower();
        followerListAdapter = new FollowerListAdapter(model.getListOfFollower().getValue(), container.getContext());
        viewPager.setAdapter(followerListAdapter);


        //Se la lista non ha elementi allora scarico un nuovo utente
        if (model.getListOfFollower().getValue().size() == 0) {
            //1) loading
            loading.setVisibility(View.VISIBLE);
            model.getFollowed();
        }

        model.getListOfFollower().observe(lifecycleOwner,
                listOfFollower -> {
                    //2) se l'utente loggato non segue altri utenti mostro un messaggio specifico
                    if (listOfFollower.size() == 0) {
                        nessunFollower.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                    //2) se l'utente loggato segue altri utenti, ottengo dal server la lista e la mostro
                    if (listOfFollower.size() != 0) {
                        nessunFollower.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                    }
                    followerListAdapter.notifyDataSetChanged();
                }
        );

        if (model.getError()){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(root.getContext());
            alertDialog.setTitle("Ops");
            alertDialog.setMessage("Si è verificato un errore durante il download della lista degli utenti seguiti");
            alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
            AlertDialog alert = alertDialog.create();
            alert.show();
        }

        //Effettua il refresh della lista degli utenti seguiti
        SwipeRefreshLayout swipeToRefresh = root.findViewById(R.id.swipeToRefresh);
        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeToRefresh.setRefreshing(true);

                model.getListOfFollower().setValue(new FollowerListRepository());
                followerListAdapter = new FollowerListAdapter(model.getListOfFollower().getValue(), container.getContext());
                model.getFollowed();
                viewPager.setAdapter(followerListAdapter);

                swipeToRefresh.setRefreshing(false);
            }
        });
    }
}
