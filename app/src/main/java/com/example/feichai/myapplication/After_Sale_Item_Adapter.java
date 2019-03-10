package com.example.feichai.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class After_Sale_Item_Adapter extends ArrayAdapter {

    private Context context;
    private int resourceId;
    private LayoutInflater layoutInflater;
    private List<After_Sale_Info_Item> list;


    public After_Sale_Item_Adapter(Context context,int resource,List<After_Sale_Info_Item> list){
        super(context, resource, list);
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
        this.resourceId = resource;
    }

    @Override
    public int getCount() {
        return list ==null?0:list.size();
    }
    public After_Sale_Info_Item getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        After_Sale_Info_Item after_sale_info_item = (After_Sale_Info_Item) getItem(position);
        View view = layoutInflater.inflate(resourceId, null);

        TextView tv_after_sale_name = (TextView)view.findViewById(R.id.after_sale_item_company_name);
        TextView tv_after_sale_date = (TextView)view.findViewById(R.id.after_sale_item_day);
        TextView tv_after_sale_time = (TextView)view.findViewById(R.id.after_sale_item_time);
        TextView tv_after_sale_money = (TextView)view.findViewById(R.id.after_sale_item_money);
        final Button tv_get_applioation = (Button)view.findViewById(R.id.after_sale_item_get_application_button);
        int after_sale_state = after_sale_info_item.getAfter_sale_state();

        final int state= after_sale_state;

        tv_after_sale_name.setText(after_sale_info_item.getAfter_sale_company_name());
        tv_after_sale_date.setText(String.valueOf(after_sale_info_item.getAfter_sale_day()));
        tv_after_sale_time.setText(String.valueOf(after_sale_info_item.getAfter_sale_time()));
        tv_after_sale_money.setText(String.valueOf(after_sale_info_item.getAfter_sale_money()));

        switch (state) {
            case 1:
                tv_get_applioation.setText("申请售后");
                break;
            case 2:
                tv_get_applioation.setText("查看");
                break;
            case 3:
                tv_get_applioation.setText("查看");
                break;
                default:
                    break;


        }

        tv_get_applioation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state){
                    case 1:
                        tv_get_applioation.setVisibility(View.VISIBLE);

                        //跳转聊天
                        break;
                    case 2:
                        tv_get_applioation.setVisibility(View.VISIBLE);

                        //跳转聊天
                        break;
                    case 3:
                        tv_get_applioation.setVisibility(View.VISIBLE);

                        //跳转评价页面
                        break;
                        default:break;

                }
            }
        });


        return view;
    }


}
