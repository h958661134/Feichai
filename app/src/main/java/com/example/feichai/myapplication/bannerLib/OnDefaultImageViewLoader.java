package com.example.feichai.myapplication.bannerLib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.feichai.myapplication.R;
import com.example.feichai.myapplication.listener.OnLoadImageViewListener;

public class OnDefaultImageViewLoader implements OnLoadImageViewListener {

    @Override
    public View createImageView(Context context, boolean isScaleAnimation) {
        View view;
        if (!isScaleAnimation) {
            view = LayoutInflater.from(context).inflate(R.layout.item_banner, null, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_animation_banner, null, false);

        }
        return view;
    }

    @Override
    public void onLoadImageView(ImageView imageView, Object parameter) {

    }
}
