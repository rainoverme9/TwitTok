package com.example.twittwok.utils.FollowerUtils;

import static androidx.navigation.Navigation.findNavController;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twittwok.MainActivity;
import com.example.twittwok.R;
import com.example.twittwok.ui.follower.followerInfo.FollowerUserInfoViewModel;
import com.example.twittwok.ui.userInfo.UserInfoTwokListViewModel;
import com.example.twittwok.utils.OnListClickEventListener;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.User;
import com.example.twittwok.utils.TwokListUtils.TwokPostRepository;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowerListHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private CircleImageView picture;
    private  View view;

    public FollowerListHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;

        picture = itemView.findViewById(R.id.picture);
        name = itemView.findViewById(R.id.name);
    }

    public void updateContent(User follower) {

        name.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", follower.getName());
            bundle.putString("uid", String.valueOf(follower.getUid()));
            findNavController(view).navigate(R.id.action_navigation_followerFragment_to_followerUserInfoFragment, bundle);

            FollowerUserInfoViewModel modelUserInfo = new ViewModelProvider(((MainActivity) view.getContext())).get(FollowerUserInfoViewModel.class);
            modelUserInfo.getListOfTwoks().setValue(new TwokPostRepository());
        });

        picture.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", follower.getName());
            bundle.putString("uid", String.valueOf(follower.getUid()));
            findNavController(view).navigate(R.id.action_navigation_followerFragment_to_followerUserInfoFragment, bundle);

            FollowerUserInfoViewModel modelUserInfo = new ViewModelProvider(((MainActivity) view.getContext())).get(FollowerUserInfoViewModel.class);
            modelUserInfo.getListOfTwoks().setValue(new TwokPostRepository());
        });

        name.setText(follower.getName());

        if (follower.getPicture() != null) {
            try {
                String pictureUser = follower.getPicture();
                byte[] decodedString = Base64.decode(pictureUser, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                picture.setImageBitmap(decodedByte);
            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Somenting went wrong");
            }

        }
    }
}
