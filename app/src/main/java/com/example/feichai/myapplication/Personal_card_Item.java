package com.example.feichai.myapplication;

public class Personal_card_Item {
    private String card_name = null;    //银行卡名字
    private String card_kind = null;    //银行卡种类
    private String card_number = null;  //银行卡号

    public Personal_card_Item(String card_name,String card_kind,String card_number){

        this.card_kind = card_kind;
        this.card_name = card_name;
        this.card_number = card_number;

    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCard_kind() {
        return card_kind;
    }

    public void setCard_kind(String card_kind) {
        this.card_kind = card_kind;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }
}
