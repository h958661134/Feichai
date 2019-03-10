package com.example.feichai.myapplication.SQLiteDataBase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Bill {
    String user_id,manage_id;
    int customer_service;
    String goods_name,goods_logo,trade_address,bill_id,bill_exchange_time;
    Timestamp bill_start_time;
    float bill_money,goods_weight;
    boolean bill_codition;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getManage_id() {
        return manage_id;
    }

    public void setManage_id(String manage_id) {
        this.manage_id = manage_id;
    }

    public int getCustomer_service() {
        return customer_service;
    }

    public void setCustomer_service(int customer_service) {
        this.customer_service = customer_service;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_logo() {
        return goods_logo;
    }

    public void setGoods_logo(String goods_logo) {
        this.goods_logo = goods_logo;
    }

    public Timestamp getBill_start_time() {
        return bill_start_time;
    }

    public void setBill_start_time(Timestamp bill_start_time) {
        this.bill_start_time = bill_start_time;
    }

    public float getBill_money() {
        return bill_money;
    }

    public void setBill_money(float bill_money) {
        this.bill_money = bill_money;
    }

    public float getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(float goods_weight) {
        this.goods_weight = goods_weight;
    }

    public boolean isBill_codition() {
        return bill_codition;
    }

    public void setBill_codition(boolean bill_codition) {
        this.bill_codition = bill_codition;
    }

    public String getTrade_address() {
        return trade_address;
    }

    public void setTrade_address(String trade_address) {
        this.trade_address = trade_address;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getBill_exchange_time() {
        return bill_exchange_time;
    }

    public void setBill_exchange_time(String bill_exchange_time) {
        this.bill_exchange_time = bill_exchange_time;
    }
}
