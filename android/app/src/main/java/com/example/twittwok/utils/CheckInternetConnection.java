package com.example.twittwok.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.twittwok.MainActivityViewModel;

public class CheckInternetConnection {
    private Context context;
    private MainActivityViewModel model;
    private NetworkRequest networkRequest;

    public CheckInternetConnection(Context context, MainActivityViewModel model, NetworkRequest networkRequest) {
        this.context = context;
        this.model = model;
        this.networkRequest = networkRequest;
    }

    @SuppressLint("MissingPermission")
    public void registerNetworkCallback() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    Log.d("Internet avaiable", "SI INTERNET");
                    model.setCheckConncetion(true);
                }
                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    Log.d("Internet not-avaiable", "NO INTERNET");
                    model.setCheckConncetion(false);
                }
                @Override
                public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities);
                    final boolean unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
                    Log.d("Internet Capabilities changed", String.valueOf(unmetered));
                }
            };
            connectivityManager.requestNetwork(networkRequest, networkCallback);
        } catch (Exception e) {
            model.setCheckConncetion(false);
        }
    }

    @SuppressLint("MissingPermission")
    public boolean firstStart() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

            if (networkCapabilities == null) {
                Log.d("Internet", "Device Offline");
                return false;
            } else {
                Log.d("Internet", "Device Online");
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }
}
