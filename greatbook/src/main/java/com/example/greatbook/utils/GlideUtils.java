package com.example.greatbook.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.greatbook.App;
import com.example.greatbook.R;

import static android.R.attr.width;
import static android.support.design.R.attr.height;

/**
 * Created by MBENBEN on 2016/11/11.
 */

public class GlideUtils {
    private static Context context= App.getInstance().getContext();
    public static void load(String url, ImageView iv) {
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    public static void load(byte[] url, ImageView iv) {
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    public static void load(int url, ImageView iv) {
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }
    public static void loadCircle( String url, ImageView iv) {
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(iv);
    }

}