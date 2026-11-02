package com.example.twittwok.utils.DataBase;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.ReuqestUtils.InterfaceRequestClass.OnRequestFailed;
import com.example.twittwok.utils.ReuqestUtils.InterfaceRequestClass.OnRequestOk;

import java.util.Objects;

public class SidRepository {
    private static String sid;

    public static void register(@NonNull Context context, OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPreferences", 0);

        //Creo e salvo il sid
        sid = sharedPreferences.getString("sid", "");

        if (Objects.equals(sid, "")) {
            CommunicationController.register(
                    newSid -> {
                        SharedPreferences.Editor editorSid = sharedPreferences.edit();
                        editorSid.putString("sid", String.valueOf(newSid));
                        editorSid.apply();
                        sid = String.valueOf(newSid);

                        onRequestOk.onResponseComplete(sid);
                    },
                    error -> onRequestFailed.onFail(error)
            );
        } else {
            onRequestOk.onResponseComplete(sid);
        }
    }

}
