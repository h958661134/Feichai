package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.UserService;

import java.util.ArrayList;
import java.util.List;

public class Setting extends Activity implements AdapterView.OnItemClickListener {

    private List<SettingItem> itemList = new ArrayList<>();     // 定义一个ArrayList存放所有要添加的item
    String user_name,password,mail,nearname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_setting);

        ActivityManager.getInstance().addActivity(this);

        initItems();                                                                                            // 生成ListView内容
        SettingItemAdapter adapter = new SettingItemAdapter(this, R.layout.setting_item, itemList);    // 声明adapter
        ListView itemsLV = (ListView) findViewById(R.id.setting_listView);                                            // 获得该ListView
        itemsLV.setAdapter(adapter);                                                                            // 设置adapter
        itemsLV.setOnItemClickListener(this);                                                                   // 监听item的click事件

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        password = intent.getStringExtra("password");
        mail = intent.getStringExtra("email");
        nearname = intent.getStringExtra("nearname");
//        UserService userService = new UserService(Setting.this);
//        nearname =  userService.get_nearname(user_name);

        //Toast.makeText(Setting.this, "用户"+user_name+"进入设置", Toast.LENGTH_LONG).show();

    }


    private void initItems() {
        SettingItem divider = new SettingItem(true);

        itemList.add(new SettingItem("账号管理", null, new Intent(this, InfoSettings.class)));
        itemList.add(divider);
        itemList.add(new SettingItem("意见反馈", null, new Intent(this, FeedbackSettings.class)));
        itemList.add(divider);
        itemList.add(new SettingItem("退出登录", null, null));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i==0) {          // 若该item有intent
            Intent intent =new Intent(Setting.this,InfoSettings.class);
            intent.putExtra("user_name",user_name);
            intent.putExtra("password",password);
            intent.putExtra("email",mail);
            intent.putExtra("nearname",nearname);
            startActivity(intent);// 传递该intent
        }
        if (i==1) {          // 若该item有intent
            Intent intent =new Intent(Setting.this,FeedbackSettings.class);
            startActivity(intent);// 传递该intent
        }
        if (i == itemList.size() - 1) {                                             // 若该item是itemList中的最后一个
            AlertDialog.Builder checkExit = new AlertDialog.Builder(this);      // 创建对话框
            checkExit.setMessage("退出登陆？");
            checkExit.setCancelable(true);
            checkExit.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    ActivityManager.getInstance().exit();
                                                                        // 选“是”则结束当前Activity
                }
            });
            checkExit.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}      // 选“否”对话框消失
            });
            checkExit.show();       // 弹出对话框
        }
    }

    public void prev(View view){
        Intent in=getIntent();
        //设置返回结果成功
        setResult(RESULT_OK, in);
        //关闭当前activity
        finish();
    }

}
