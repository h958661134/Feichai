package com.example.feichai.myapplication;

import android.widget.TextView;

import java.sql.Timestamp;

public class My_Transaction_info_Item {

    private String transaction_company_name;//交易的物品名字
    //private String transaction_day;
    Timestamp transaction_time;
    private String transaction_money;

    public My_Transaction_info_Item(String transaction_company_name,String transaction_money,Timestamp transaction_time){
        this.transaction_company_name = transaction_company_name;
        this.transaction_money = transaction_money;
        this.transaction_time = transaction_time;
    }

    public String getTransaction_company_name() {
        return transaction_company_name;
    }

    public void setTransaction_company_name(String transaction_company_name) {
        this.transaction_company_name = transaction_company_name;
    }

    public Timestamp getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(Timestamp transaction_time) {
        this.transaction_time = transaction_time;
    }

    public String getTransaction_money() {
        return transaction_money;
    }

    public void setTransaction_money(String transaction_money) {
        this.transaction_money = transaction_money;
    }
}
