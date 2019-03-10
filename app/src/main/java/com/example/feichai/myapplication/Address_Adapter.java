package com.example.feichai.myapplication;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;


public class Address_Adapter extends BaseAdapter {

    private Context context;
    private List<Address_Items> list;
    public LayoutInflater layoutInflater;
    public String new_address;
    Map<String,String> map;


    public Address_Adapter(Context context, List<Address_Items> list, Map<String,String> map) {
        this.context = context;
        this.list = list;
        this.map = map;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list ==null?0:list.size();
    }
    public Address_Items getItem(int position){
        return list.get(position);
    }
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.address_item,null);
        Address_Items address_items =getItem(position);
        final EditText address = (EditText)view.findViewById(R.id.item_address);
        address.setFocusable(false);
        address.setFocusableInTouchMode(false);

        TextView use = (TextView)view.findViewById(R.id.use);
        TextView edit = (TextView)view.findViewById(R.id.edit);
        TextView delete = (TextView)view.findViewById(R.id.delete);
        // 设置文本
        address.setText(address_items.getAddress());

        final SlideLayout slideLayout = (SlideLayout) view;

        final Address_Items item = list.get(position);

        //删除
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(item);
                map.remove(item);
                notifyDataSetChanged();

            }
        });

        //应用
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_address = address.getText().toString();
                item.setAddress(new_address);
                //map.put("地址"+(position+1),new_address);
                Address.change_Address(item.getAddress(),position);
                Address.set_Address(item.getAddress());
                slideLayout.closeMenu();

            }
        });


        //编辑
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address.setFocusableInTouchMode(true);
                address.setFocusable(true);
                address.requestFocus();
                slideLayout.closeMenu();
                address.selectAll();
            }
        });

        slideLayout.setOnStateChangeListener(new MyOnStateChangeListener());

        return view;
    }


    public SlideLayout slideLayout = null;
    class MyOnStateChangeListener implements SlideLayout.OnStateChangeListener
    {

        @Override
        public void onOpen(SlideLayout layout) {

            slideLayout = layout;
        }

        @Override
        public void onMove(SlideLayout layout) {
            if (slideLayout != null && slideLayout !=layout)
            {
                slideLayout.closeMenu();
            }
        }

        @Override
        public void onClose(SlideLayout layout) {
            if (slideLayout == layout)
            {
                slideLayout = null;
            }
        }
    }

}
