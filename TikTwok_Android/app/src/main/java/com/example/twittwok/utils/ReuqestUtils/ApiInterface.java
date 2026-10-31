package com.example.twittwok.utils.ReuqestUtils;

import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Followed;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Picture;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.User;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.IsFollowed;
import com.example.twittwok.utils.ReuqestUtils.SendRequestClass.SendTwok;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Profile;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Sid;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Twok;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("register")
    Call<Sid> register();

    @POST("getTwok")
    Call<Twok> getTwok(@Body SendTwok twok);


    @FormUrlEncoded
    @POST("getProfile")
    Call<Profile> getProfile(@Field("sid") String sid);

    @FormUrlEncoded
    @POST("setProfile")
    Call<Void> setProfile(@Field("sid") String sid,
                          @Field("name") String name,
                          @Field("picture") String picture);

    @FormUrlEncoded
    @POST("getTwok")
    Call<Twok> getTwok(@Field("sid") String sid,
                       @Field("uid") String uid,
                       @Field("tid") String tid);

    @POST("addTwok")
    Call<Void> addTwok(@Body SendTwok sendTwok);

    @FormUrlEncoded
    @POST("getPicture")
    Call<Picture> getPicture(@Field("sid") String sid,
                             @Field("uid") String uid);

    @FormUrlEncoded
    @POST("follow")
    Call<Void> follow(@Field("sid") String sid,
                      @Field("uid") String uid);

    @FormUrlEncoded
    @POST("unfollow")
    Call<Void> unfollow(@Field("sid") String sid,
                        @Field("uid") String uid);

    @FormUrlEncoded
    @POST("getFollowed")
    Call<List<User>> getFollowed(@Field("sid") String sid);

    @FormUrlEncoded
    @POST("isFollowed")
    Call<IsFollowed> isFollowed(@Field("sid") String sid,
                                @Field("uid") String uid);
}
