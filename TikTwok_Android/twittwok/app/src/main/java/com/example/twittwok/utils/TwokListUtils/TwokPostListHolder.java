package com.example.twittwok.utils.TwokListUtils;

import static androidx.navigation.Navigation.findNavController;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.example.twittwok.MainActivity;
import com.example.twittwok.R;

import com.example.twittwok.ui.userInfo.UserInfoTwokListViewModel;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Twok;


import java.util.ArrayList;
import java.util.Arrays;


import de.hdodenhof.circleimageview.CircleImageView;

public class TwokPostListHolder extends RecyclerView.ViewHolder {
    private TextView name, text, position;
    private LinearLayout twokpost;
    private CircleImageView picture;

    private View view;

    private boolean follower = true;

    public TwokPostListHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;

        picture = itemView.findViewById(R.id.picture);
        name = itemView.findViewById(R.id.name);
        position = itemView.findViewById(R.id.position);
        text = itemView.findViewById(R.id.text);
        twokpost = itemView.findViewById(R.id.twokpost);;
    }

    public void updateContent(Twok twoksPosts, boolean actionToUserInfo) {
        //Quando scorro la lista setto i valori di picture, nome, pozione e twok

        if (actionToUserInfo) {
            name.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", twoksPosts.getName());
                bundle.putString("uid", String.valueOf(twoksPosts.getUid()));
                findNavController(view).navigate(R.id.action_global_toUserInfo, bundle);

                UserInfoTwokListViewModel modelUserInfo = new ViewModelProvider(((MainActivity) view.getContext())).get(UserInfoTwokListViewModel.class);
                modelUserInfo.getListOfTwoks().setValue(new TwokPostRepository());
            });

            picture.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", twoksPosts.getName());
                bundle.putString("uid", String.valueOf(twoksPosts.getUid()));
                findNavController(view).navigate(R.id.action_global_toUserInfo, bundle);
                UserInfoTwokListViewModel modelUserInfo = new ViewModelProvider(((MainActivity) view.getContext())).get(UserInfoTwokListViewModel.class);
                modelUserInfo.getListOfTwoks().setValue(new TwokPostRepository());
            });
        }

        if (twoksPosts.getLat() != null && twoksPosts.getLon() != null) {
            position.setText("📍 Visualizza Mappa");
            position.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", twoksPosts.getLat());
                bundle.putDouble("lon", twoksPosts.getLon());
                findNavController(view).navigate(R.id.action_global_toMap, bundle);
            });
        }

        if (twoksPosts.getPicture() != null) {
            try {
                String pictureUser = twoksPosts.getPicture();
                byte[] decodedString = Base64.decode(pictureUser, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                picture.setImageBitmap(decodedByte);
            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Somenting went wrong");
            }
        }

        name.setText(twoksPosts.getName());
        text.setText(twoksPosts.getText());

        twokpost.setBackgroundColor(checkColor(twoksPosts.getBgcol(), "bgcol"));
        text.setTextColor(checkColor(twoksPosts.getFontcol(), "fontcol"));

        text.setTextSize(twoksPosts.getFontsize() == 0 ? 20 : twoksPosts.getFontsize() == 1 ? 30 : twoksPosts.getFontsize() == 2 ? 40 : 30);
        text.setTypeface(twoksPosts.getFonttype() == 0 ? Typeface.MONOSPACE : twoksPosts.getFonttype() == 1 ? Typeface.SANS_SERIF : twoksPosts.getFonttype() == 2 ? Typeface.SERIF : Typeface.SANS_SERIF);
        text.setTextAlignment(twoksPosts.getHalign() == 0 ? View.TEXT_ALIGNMENT_TEXT_START : twoksPosts.getHalign() == 1 ? View.TEXT_ALIGNMENT_CENTER : twoksPosts.getHalign() == 2 ? View.TEXT_ALIGNMENT_TEXT_END : View.TEXT_ALIGNMENT_CENTER);
        text.setGravity(twoksPosts.getValign() == 0 ? Gravity.TOP : twoksPosts.getValign() == 1 ? Gravity.CENTER_VERTICAL : twoksPosts.getValign() == 2 ? Gravity.BOTTOM : Gravity.CENTER_VERTICAL);
    }

    public int checkColor(String color, String specificCase) {
        ArrayList<String> acceptedColors = new ArrayList<>(Arrays.asList("red", "blue", "green", "black", "white", "gray", "cyan", "magenta", "yellow", "lightgray", "darkgray", "grey", "lightgrey", "darkgrey", "aqua", "fuchsia", "lime", "maroon", "navy", "olive", "purple", "silver", "teal"));

        if (color.matches("^[0-9A-Fa-f]+$")) {
            if(color.length() == 6) {
                return (Color.parseColor("#" + color.toUpperCase()));
            }else {
                StringBuilder temp = new StringBuilder(color);
                while(temp.length()<6) {
                    temp.append(temp.charAt(0));
                }
                return (Color.parseColor("#" + temp.toString().toUpperCase()));
            }
        }else{
            String stringColor = color.toLowerCase();
            if(acceptedColors.contains(stringColor)) {
                return (Color.parseColor(color));
            }else {
                if (specificCase.equals("bgcol")) {
                    return (Color.parseColor("#FFFFFF"));
                } else {
                    return (Color.parseColor("#000000"));
                }
            }
        }
    }
}
