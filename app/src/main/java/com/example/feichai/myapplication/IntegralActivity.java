package com.example.feichai.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class IntegralActivity extends AppCompatActivity {
    private List<Integral> integrallist = new ArrayList<Integral>();
    public Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_integral_exchange);
        initIntegral();
        integralAdapter integraladapter = new integralAdapter(IntegralActivity.this, R.layout.integral_listview, integrallist);
        ListView listview = (ListView) findViewById(R.id.lv_integral);
        listview.setAdapter(integraladapter);

        ActivityManager.getInstance().addActivity(this);

    }


    private void initIntegral() {
        Integral dujinshuqian = new Integral("镀金镂空书签", R.drawable.dujinloukong, "88");
        integrallist.add(dujinshuqian);
        Integral zhizihua = new Integral("栀子花盆栽", R.drawable.zhizihua, "200");
        integrallist.add(zhizihua);
        Integral yusan = new Integral("晴雨伞", R.drawable.yusan, "500");
        integrallist.add(yusan);
        Integral xiuzhenyezi = new Integral("袖珍椰子盆栽", R.drawable.xiuzhenyezi, "200");
        integrallist.add(xiuzhenyezi);
        Integral chuangyishuqian = new Integral("中国风书签", R.drawable.zhongguofeng, "20");
        integrallist.add(chuangyishuqian);
        Integral xiangshui = new Integral("气味图书馆", R.drawable.qiweitushuguan, "300");
        integrallist.add(xiangshui);
        Integral dujinshuqian1 = new Integral("镀金镂空书签", R.drawable.dujinloukong, "88");
        integrallist.add(dujinshuqian1);
        Integral zhizihua1 = new Integral("栀子花盆栽", R.drawable.zhizihua, "200");
        integrallist.add(zhizihua1);
        Integral yusan1 = new Integral("晴雨伞", R.drawable.yusan, "270");
        integrallist.add(yusan1);
        Integral xiuzhenyezi1 = new Integral("袖珍椰子盆栽", R.drawable.xiuzhenyezi, "588");
        integrallist.add(xiuzhenyezi1);
        Integral chuangyishuqian1 = new Integral("中国风书签", R.drawable.zhongguofeng, "288");
        integrallist.add(chuangyishuqian1);
        Integral xiangshui1 = new Integral("气味图书馆", R.drawable.qiweitushuguan, "88");
        integrallist.add(xiangshui1);
    }
}
