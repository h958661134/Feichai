package com.example.feichai.myapplication;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class After_Sale_Page_Adapter extends PagerAdapter {

    Context context;
    List<Integer> list;
    List<String> stringList;
    List<After_Sale_Info_Item> get_application_Items;
    List<After_Sale_Info_Item> application_online_Items;
    List<After_Sale_Info_Item> application_list_Items;
    LinearLayout get_application_linearLayout;
    LinearLayout application_online_linearLayout;
    LinearLayout application_list_linearLayout;

    public After_Sale_Page_Adapter(Context context, List<Integer> list, List<String> stringList) {
        this.context = context;
        this.list = list;
        this.stringList = stringList;
    }

    public void init(){
        get_application_Items = new ArrayList<>();
        get_application_Items.add(new After_Sale_Info_Item("湖北废纸回收场","07-19","37.00","15:39",1));
        get_application_Items.add(new After_Sale_Info_Item("湖北废纸回收场","07-19","37.00","15:39",1));

        application_online_Items = new ArrayList<>();
        application_online_Items.add(new After_Sale_Info_Item("湖北废纸回收场","07-19","37.00","15:39",2));
        application_online_Items.add(new After_Sale_Info_Item("湖北废纸回收场","07-19","37.00","15:39",2));


        application_list_Items = new ArrayList<>();
        application_list_Items.add(new After_Sale_Info_Item("湖北废纸回收场","07-19","37.00","15:39",2));
        application_list_Items.add(new After_Sale_Info_Item("湖北废纸回收场","07-19","37.00","15:39",2));



    }

    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        init();
        int item = list.get(position);
        View view = View.inflate(context, item, null);
        if (item == R.layout.after_sale_application) {
            ListView after_application_LV = (ListView) view.findViewById(R.id.after_application_LV);
            after_application_LV.setAdapter(new After_Sale_Item_Adapter(context,R.layout.after_sale_item,get_application_Items));
            after_application_LV.setDivider(null);

            get_application_linearLayout = (LinearLayout)view.findViewById(R.id.after_application_linearLayout);

        } else if (item == R.layout.after_sale_online) {
            ListView after_online_LV = (ListView) view.findViewById(R.id.after_online_LV);
            after_online_LV.setAdapter(new After_Sale_Item_Adapter(context,R.layout.after_sale_item,application_online_Items));
            after_online_LV.setDivider(null);
            application_online_linearLayout = (LinearLayout)view.findViewById(R.id.after_online_linearLayout);

        } else if (item == R.layout.after_sale_list) {
            ListView after_list_LV = (ListView) view.findViewById(R.id.after_list_LV);
            after_list_LV.setAdapter(new After_Sale_Item_Adapter(context,R.layout.after_sale_item,application_list_Items));
            after_list_LV.setDivider(null);
            application_list_linearLayout = (LinearLayout)view.findViewById(R.id.after_list_linearLayout);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }


}
