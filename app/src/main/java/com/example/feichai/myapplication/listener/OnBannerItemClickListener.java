package com.example.feichai.myapplication.listener;

import com.example.feichai.myapplication.bean.BannerInfo;

import java.util.ArrayList;

public interface OnBannerItemClickListener {
    /**
     * banner click
     *
     * @param index  subscript
     * @param banner bean
     */
    void onBannerClick(int index, ArrayList<BannerInfo> banner);
}
