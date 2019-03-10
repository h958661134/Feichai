package com.example.feichai.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class My_Transaction_Item_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<My_Transaction_info_Item> list;

    public My_Transaction_Item_Adapter(){}

    public My_Transaction_Item_Adapter(Context context,List<My_Transaction_info_Item> list){
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list ==null?0:list.size();
    }
    public My_Transaction_info_Item getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.my_transaction_item,null);
        My_Transaction_info_Item my_transaction_info_item = getItem(position);

        TextView tv_transaction_name = (TextView)view.findViewById(R.id.my_transaction_item_company_name);
        TextView tv_transaction_time = (TextView)view.findViewById(R.id.my_transaction_item_company_time);
        TextView tv_transaction_money = (TextView)view.findViewById(R.id.my_transaction_item_money);

        tv_transaction_name.setText(my_transaction_info_item.getTransaction_company_name());
        tv_transaction_time.setText(String.valueOf(my_transaction_info_item.getTransaction_time()));
        tv_transaction_money.setText(String.valueOf(my_transaction_info_item.getTransaction_money()));

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
