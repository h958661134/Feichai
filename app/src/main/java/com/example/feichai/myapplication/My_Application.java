package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class My_Application extends Activity {

    private ListView listView;
    private Card_Item_Adapter cardItemAdapter;
    private List<Personal_card_Item> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_my_application);

        ActivityManager.getInstance().addActivity(this);
        initItems();
        listView = (ListView)findViewById(R.id.my_application_card_listView);
        cardItemAdapter = new Card_Item_Adapter(this,list);
        listView.setAdapter(cardItemAdapter);
    }

    public void initItems(){

        list.add(new Personal_card_Item("工商银行","储蓄卡","1346 1236 7942"));
        list.add(new Personal_card_Item("中国银行","储蓄卡","7892 9642 7942"));
        list.add(new Personal_card_Item("民生银行","储蓄卡","9671 1236 3674"));

    }

    public void prev(View view){
        Intent in=getIntent();
        //设置返回结果成功
        setResult(RESULT_OK, in);
        //关闭当前activity
        finish();
    }


}
