package com.example.feichai.myapplication;

import android.content.Intent;

public class My_Footprint_info_Item {
    private String footprint_company_name;
    private Intent intent = null;       // 点击后跳转的intent


    public My_Footprint_info_Item(String footprint_company_name, Intent intent){
        this.footprint_company_name = footprint_company_name;
        this.intent = intent;

    }


    public String getFootprint_company_name() {
        return footprint_company_name;
    }

    public void setFootprint_company_name(String footprint_company_name) {
        this.footprint_company_name = footprint_company_name;
    }


    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
