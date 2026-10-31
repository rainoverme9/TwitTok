package com.example.twittwok.ui.userInfo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.twittwok.R;
import com.example.twittwok.utils.TwokListUtils.TwokPostListAdapter;
import com.example.twittwok.utils.TwokListUtils.TwokPostRepository;

//Mostra la bacheca specifica dell'utente selezionato
public class UserInfoListOfTwoks {

    static TwokPostListAdapter twokPostListAdapter;

    public static void getTwok(View root, ViewGroup container, String uid, UserInfoTwokListViewModel model, LifecycleOwner lifecycleOwner) {

        //Seleziono i componenti del layout
        ViewPager2 viewPager = root.findViewById(R.id.viewPager);
        LinearLayout loading = root.findViewById(R.id.loading);
        //1) loading
        loading.setVisibility(View.VISIBLE);

        //2) creo la lista dei twok
        model.getListOfTwoks();
        twokPostListAdapter = new TwokPostListAdapter(model.getListOfTwoks().getValue(), container.getContext(), false);
        viewPager.setAdapter(twokPostListAdapter);

        //Se la lista non ha elementi allora scarico un nuovo twok
        if (model.getListOfTwoks().getValue().size() == 0) {
            model.downloadOneMoreTwok(uid);
        }
        model.getListOfTwoks().observe(lifecycleOwner,
                listOfTwoks -> {
                    //3) quando ho ottenuto i twok dal server mostro la lista
                    if (listOfTwoks.size() != 0) {
                        loading.setVisibility(View.GONE);
                    }
                    //ottengo il buffer iniziale
                    if (listOfTwoks.size() < 5) {
                        model.downloadOneMoreTwok(uid);
                    }
                    twokPostListAdapter.notifyDataSetChanged();
                }
        );

        /*if (model.getError()){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(root.getContext());
            alertDialog.setTitle("Ops");
            alertDialog.setMessage("Si è verificato un errore durante il download dei twok dal server");
            alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
            AlertDialog alert = alertDialog.create();
            alert.show();
        }*/

        //Ritorna e aggiunge un nuovo twok al buffer dopo lo scroll della lista dei twok
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (viewPager.getCurrentItem() > model.size()-2) {
                    model.downloadOneMoreTwok(uid);
                }
            }
        });

        //Effettua il refresh della lista dei twok
        SwipeRefreshLayout swipeToRefresh = root.findViewById(R.id.swipeToRefresh);
        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeToRefresh.setRefreshing(true);

                model.getListOfTwoks().setValue(new TwokPostRepository());
                twokPostListAdapter = new TwokPostListAdapter(model.getListOfTwoks().getValue(), container.getContext(), true);
                viewPager.setAdapter(twokPostListAdapter);

                swipeToRefresh.setRefreshing(false);
            }
        });
    }
}
