package com.example.twittwok.utils.ReuqestUtils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.twittwok.utils.DataBase.SidRepository;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Followed;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.IsFollowed;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Picture;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Profile;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Sid;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Twok;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.User;
import com.example.twittwok.utils.ReuqestUtils.InterfaceRequestClass.OnFollowerOk;
import com.example.twittwok.utils.ReuqestUtils.InterfaceRequestClass.OnRequestFailed;
import com.example.twittwok.utils.ReuqestUtils.InterfaceRequestClass.OnRequestOk;
import com.example.twittwok.utils.ReuqestUtils.SendRequestClass.SendTwok;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunicationController {
    //static String sid = "wVOGrbZIqM1gRbOyz0q9";
    //static String sid = "gZjWMb3OLaJNudJgN3X6";

    static String sid;
    public static void setSid(String sid) {
        CommunicationController.sid = sid;
    }


    public static ApiInterface GetApiInterface() {
        String BASE_URL = "https://develop.ewlab.di.unimi.it/mc/twittok/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);    //Rappresenta tutte le chiamate
        return apiInterface;
    }

    public static void register(OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        Call<Sid> registerCall = GetApiInterface().register();
        registerCall.enqueue(new Callback<Sid>() {
            @Override
            public void onResponse(@NonNull Call<Sid> call, @NonNull Response<Sid> response) {
                if (response.code() == 200) {
                    onRequestOk.onResponseComplete(response.body().getSid());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Sid> call, @NonNull Throwable error) {
                onRequestFailed.onFail(error);
            }
        });
    }

    public static void getProfile(OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        Call<Profile> profileCall = GetApiInterface().getProfile(sid);
        profileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                if (response.code() == 200) {
                    onRequestOk.onResponseComplete(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable error) {
                onRequestFailed.onFail(error);
            }
        });
    }

    public static void setProfile(String name, String picture, OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        Call<Void> setPictureCall = GetApiInterface().setProfile(sid, name, picture);
        setPictureCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    onRequestOk.onResponseComplete(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                onRequestFailed.onFail(null);
            }
        });
    }

    public static void getTwok(String uid, String tid, OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        if (sid != null) {Log.d("Sid_Communication", sid);}
        Call<Twok> twokCall = GetApiInterface().getTwok(sid, uid, tid);
        twokCall.enqueue(new Callback<Twok>() {
            @Override
            public void onResponse(@NonNull Call<Twok> call, @NonNull Response<Twok> response) {
                if (response.code() == 200) {
                    onRequestOk.onResponseComplete(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Twok> call, @NonNull Throwable error) {
                onRequestFailed.onFail(error);
            }
        });
    }

    public  static  void getPicture(String uid, OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        Call<Picture> pictureCall = GetApiInterface().getPicture(sid, uid);
        pictureCall.enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                if (response.code() == 200) {
                    onRequestOk.onResponseComplete(response.body());
                }
            }
            @Override
            public void onFailure(Call<Picture> call, Throwable error) {
                onRequestFailed.onFail(error);
            }
        });
    }


    public static void addTwok(SendTwok twok, OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        twok.setSid(sid);
        Call<Void> addTwokCall = GetApiInterface().addTwok(twok);
        addTwokCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    onRequestOk.onResponseComplete(null);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable error) {
                onRequestFailed.onFail(error);
            }
        });
    }

    public static void getFollowed(OnFollowerOk onRequestOk, OnRequestFailed onRequestFailed) {
        Call<List<User>> followersCall = GetApiInterface().getFollowed(sid);
        followersCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.code() == 200) {
                    Log.d("User", String.valueOf(response.body()));
                    onRequestOk.onResponseComplete(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable error) {
                Log.d("Error_User", String.valueOf(error));
                onRequestFailed.onFail(error);
            }

        });
    }

    public static void follow(String uid, OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        Call<Void> followCall = GetApiInterface().follow(sid, uid);
        followCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    onRequestOk.onResponseComplete(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                onRequestFailed.onFail(null);
            }
        });
    }

    public static void unFollow(String uid, OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        Call<Void> unFollowCall = GetApiInterface().unfollow(sid, uid);
        unFollowCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    onRequestOk.onResponseComplete(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                onRequestFailed.onFail(null);
            }
        });
    }

    public  static  void isFollowed(String uid, OnRequestOk onRequestOk, OnRequestFailed onRequestFailed) {
        Call<IsFollowed> isFollowedCall = GetApiInterface().isFollowed(sid, uid);
        isFollowedCall.enqueue(new Callback<IsFollowed>() {
            @Override
            public void onResponse(Call<IsFollowed> call, Response<IsFollowed> response) {
                if (response.code() == 200) {
                    Log.d("Response_isfollowed", sid + " - " +uid+ " - "+ response.body().getIsFollowed());
                    onRequestOk.onResponseComplete(response.body().getIsFollowed());
                }
            }
            @Override
            public void onFailure(Call<IsFollowed> call, Throwable error) {
                onRequestFailed.onFail(error);
                Log.d("Error_IsFollowed_CC", error.toString());
            }
        });
    }
}
