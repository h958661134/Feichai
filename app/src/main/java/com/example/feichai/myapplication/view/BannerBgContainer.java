package com.example.feichai.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.feichai.myapplication.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class BannerBgContainer extends RelativeLayout {

    private List<BannerBgView> bannerBgViews = new ArrayList<>();


    public BannerBgContainer(Context context) {
        super(context);
    }

    public BannerBgContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerBgContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public List<BannerBgView> getBannerBgViews() {
        return this.bannerBgViews;
    }

    /**
     * 设置
     *
     * @param context
     * @param bgUrlList
     */
    public void setBannerBackBg(Context context, List<Object> bgUrlList) {
        bannerBgViews.clear();
        this.removeAllViews();
        FrameLayout. LayoutParams layoutParams = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin = -DensityUtil.dp2px(20);
        layoutParams.rightMargin = -DensityUtil.dp2px(20);
        for (Object urlImageView : bgUrlList) {
            BannerBgView bannerBgView = new BannerBgView(context);
            bannerBgView.setLayoutParams(layoutParams);
            Glide.with(context).load(urlImageView).into(bannerBgView.getImageView());
            bannerBgViews.add(bannerBgView);
            this.addView(bannerBgView);
        }
        bannerBgViews.get(0).bringToFront();
    }
}
