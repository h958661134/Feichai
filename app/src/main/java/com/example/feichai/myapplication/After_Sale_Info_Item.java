package com.example.feichai.myapplication;

public class After_Sale_Info_Item {

    private String after_sale_company_name;
    private String after_sale_day;
    private String after_sale_time;
    private String after_sale_money;
    private int after_sale_state;

    public After_Sale_Info_Item(String after_sale_company_name,String after_sale_day,String after_sale_money,String after_sale_time,int after_sale_state){
        this.after_sale_company_name = after_sale_company_name;
        this.after_sale_day = after_sale_day;
        this.after_sale_money = after_sale_money;
        this.after_sale_time = after_sale_time;
        this.after_sale_state = after_sale_state;
    }

    public String getAfter_sale_company_name() {
        return after_sale_company_name;
    }

    public void setAfter_sale_company_name(String after_sale_company_name) {
        this.after_sale_company_name = after_sale_company_name;
    }

    public String getAfter_sale_day() {
        return after_sale_day;
    }

    public void setAfter_sale_day(String after_sale_day) {
        this.after_sale_day = after_sale_day;
    }

    public String getAfter_sale_time() {
        return after_sale_time;
    }

    public void setAfter_sale_time(String after_sale_time) {
        this.after_sale_time = after_sale_time;
    }

    public String getAfter_sale_money() {
        return after_sale_money;
    }

    public void setAfter_sale_money(String after_sale_money) {
        this.after_sale_money = after_sale_money;
    }

    public int getAfter_sale_state() {
        return after_sale_state;
    }

    public void setAfter_sale_state(int after_sale_state) {
        this.after_sale_state = after_sale_state;
    }
}
