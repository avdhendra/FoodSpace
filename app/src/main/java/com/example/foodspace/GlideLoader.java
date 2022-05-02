package com.example.foodspace;

import android.content.Context;
import android.net.Uri;

import com.bumptech.glide.Glide;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class GlideLoader {
    Context context;

    public GlideLoader(Context context) {
        this.context = context;
    }

    public void loadUserPicture(Uri image, CircleImageView imageView) {
        Glide.with(context).load(image).centerCrop().placeholder(R.drawable.defaultavatar).into(imageView);
    }
}
