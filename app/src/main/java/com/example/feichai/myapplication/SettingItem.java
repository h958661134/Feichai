package com.example.feichai.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;

public class SettingItem {          // Setting中自定义item的实体类
    private Intent intent = null;       // 点击后跳转的intent
    private String text = null;         // 内容文字
    private String detailText=null;   // detail部分文字
    private int leftIconId = 0;         // 左侧图标id
    private int userHead;           // 右侧头像id
    private boolean showNext = true;    // 是否显示next箭头
    private boolean isDivider = false;  // 是否为分割线
    private boolean showSwitch = false; // 是否显示switch

    public SettingItem(boolean isDivider) {
        this.isDivider = isDivider;
    }

    public SettingItem(String text, String detailText, Intent intent) {
        this.text = text;
        this.detailText = detailText;
        this.intent = intent;
    }

    public SettingItem(String text, int userHead, Intent intent) {
        this.text = text;
        this.userHead = userHead;
        this.intent = intent;
    }

    public SettingItem(String text, boolean showSwitch, Intent intent) {
        this.text = text;
        this.showSwitch = showSwitch;
        this.intent = intent;
    }

    public boolean isDivider() {
        return isDivider;
    }

    public void setDivider(boolean divider) {
        isDivider = divider;
    }

    public int getLeftIconId() {
        return leftIconId;
    }

    public void setLeftIconId(int leftIconId) {
        this.leftIconId = leftIconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetailText() {
        return detailText;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getUserHead() {
        return userHead;
    }

    public void setUserHead(int userHead) {
        this.userHead = userHead;
    }

    public boolean showSwitch() {
        return showSwitch;
    }

    public void setShowSwitch(boolean showSwitch) {
        this.showSwitch = showSwitch;
    }
}
