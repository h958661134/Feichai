package com.example.feichai.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class My_Footprint_Item_Adapter extends BaseAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<My_Footprint_info_Item> list;


    public My_Footprint_Item_Adapter(){}

    public My_Footprint_Item_Adapter(Context context,List<My_Footprint_info_Item> list){
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);

    }


    public int getCount() {
        return list ==null?0:list.size();
    }
    public My_Footprint_info_Item getItem(int position){
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.my_footprint_item,null);
        My_Footprint_info_Item my_footprint_info_item = getItem(position);

        TextView tv_footprint_name = (TextView)view.findViewById(R.id.my_footprint_item_company_name);


        tv_footprint_name.setText(my_footprint_info_item.getFootprint_company_name());


        return view;
    }
}
