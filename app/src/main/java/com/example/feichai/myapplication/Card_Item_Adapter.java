package com.example.feichai.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

public class Card_Item_Adapter extends BaseAdapter {

    private List<Personal_card_Item> listViews;
    private LayoutInflater layoutInflater;
    private Context context;

    public Card_Item_Adapter(){}

    public Card_Item_Adapter(Context context,List<Personal_card_Item> listViews ){
        this.context = context;
        this.listViews = listViews;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listViews ==null?0:listViews.size();
    }

    @Override
    public Personal_card_Item getItem(int position) {
        return listViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.my_application_card_item,null);
        Personal_card_Item personal_card_item = getItem(position);

        TextView tv_card_name = (TextView)view.findViewById(R.id.application_card_name);
        TextView tv_card_kind = (TextView)view.findViewById(R.id.application_card_kind);
        TextView tv_card_number = (TextView)view.findViewById(R.id.application_card_number);

        tv_card_name.setText(personal_card_item.getCard_name());
        tv_card_kind.setText(personal_card_item.getCard_kind());
        tv_card_number.setText(String.valueOf(personal_card_item.getCard_number()));

        return view;
    }
}
