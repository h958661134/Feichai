package com.example.feichai.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BannerBgView extends RevealLayout {

    private ImageView imageView;

    public BannerBgView(Context context) {
        super(context);
        addImageView(context);
    }

    public BannerBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addImageView(context);
    }

    public BannerBgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addImageView(context);
    }


    public void addImageView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(imageView);
    }


    public ImageView getImageView() {
        return imageView;
    }
}
