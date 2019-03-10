package com.example.feichai.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.SQLiteDataBase.User;
import com.example.feichai.myapplication.SQLiteDataBase.UserService;
import com.mob.wrappers.AnalySDKWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InfoSettings extends Activity implements AdapterView.OnItemClickListener {

    private static final String TAG = "InfoSettings";
    private List<SettingItem> itemList = new ArrayList<>();     // 定义一个ArrayList存放所有要添加的item
    private Calendar calendar;
    private int year, month, day;
    private int user_pic;
    private TextView itemDetailTV;
    private int choice;
    String user_name,user_nearname,email,password;
    SettingItemAdapter adapter;
    ListView itemsLV;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_info);

        ActivityManager.getInstance().addActivity(this);
                                                                                                 // 生成ListView内容
        this.adapter = new SettingItemAdapter(this, R.layout.setting_item, itemList);    // 声明adapter
        this.itemsLV = (ListView) findViewById(R.id.settingsInfoLV);                                        // 获得该ListView
        itemsLV.setAdapter(adapter);                                                                            // 设置adapter
        itemsLV.setOnItemClickListener(this);                                                                   // 监听item的click事件

        getDate();

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        user_nearname = intent.getStringExtra("nearname");

        initItems();
    }
    private void initItems() {

        itemList.add(new SettingItem("头像", R.drawable.head_sculpture, new Intent(this,Info_Change_Headpic.class)));
        itemList.add(new SettingItem("账号", user_name, null));
        itemList.add(new SettingItem("密码 ", R.drawable.next, new Intent(this,Info_Find_password.class)));
        itemList.add(new SettingItem("邮箱", email, null));
        itemList.add(new SettingItem("性别", "男", null));
        itemList.add(new SettingItem("生日", "2018 - 7 - 17", null));
        itemList.add(new SettingItem("昵称",user_nearname,null));
        //Toast.makeText(InfoSettings.this, "user_nearname:"+ user_nearname,Toast.LENGTH_SHORT).show();
    }

    private void getDate() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, int i, long l) {
        itemDetailTV = view.findViewById(R.id.itemDetailTV);

        if (itemList.get(i).getIntent() != null) {          // 若该item有intent
            itemList.get(i).getIntent().putExtra("name",user_name);
            startActivity(itemList.get(i).getIntent());     // 传递该intent
        }

        else if (itemList.get(i).getText().equals("性别")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请选择性别：");
            final String[] sex = {"男", "女"};
            builder.setSingleChoiceItems(sex, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    choice = which;
                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    itemDetailTV.setText(sex[choice]);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });
            builder.show();
        }

        else if (itemList.get(i).getText().equals("生日")) {
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int year, int month, int day) {
                    itemDetailTV.setText(year + " - " + (month + 1) + " - " + day);
                }
            };
            DatePickerDialog dialog = new DatePickerDialog(this, R.style.MyDatePickerDialogTheme, listener, year, month, day);
            dialog.show();
        }

        else if (itemList.get(i).getText().equals("昵称")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("修改昵称");

            final View v =  LayoutInflater.from(getApplication()).inflate(R.layout.main_info_dialog, null);
            final EditText username = (EditText)v.findViewById(R.id.Info_near_name);
            username.setText(user_nearname);

            builder.setView(v);

            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText editText_username = (EditText)v.findViewById(R.id.Info_near_name);
                    user_nearname = editText_username.getText().toString();

                    user.setUser_nearname(user_nearname);

                    new Thread(runnable1).start();

                    //Toast.makeText(InfoSettings.this, "user_nearname:"+ user_nearname,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(InfoSettings.this, "nearname:"+ user_nearname,Toast.LENGTH_SHORT).show();

                    itemDetailTV.setText(user_nearname);

                    for(int j=0;j<itemList.size();j++){
                        if (itemList.get(j).getText().equals("昵称")) {
                            itemList.remove(j);
                        }
                    }
                    itemList.add(new SettingItem("昵称",user_nearname,null));

                    adapter.notifyDataSetChanged();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.show();

        }

    }

    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {

            System.out.println("进入昵称更新");
            Connection con = DatabaseHelper.getCon("root","123456");
            System.out.println(con);
            System.out.println("进入更新");

            String sql = "update user_nearname set user_nearname = "+user.getUser_nearname()+" where user_name = "+user_name;
            int i = 0;

            PreparedStatement pstmt;

            try{
                pstmt = (PreparedStatement)con.prepareStatement(sql);
                i = pstmt.executeUpdate();
                System.out.println("result"+i);
                pstmt.close();
                con.close();

                Looper.prepare();
                Log.i("TAG","更新成功");
                //Toast.makeText(InfoSettings.this, "更新成功", Toast.LENGTH_LONG).show();
                Toast.makeText(InfoSettings.this, "nearname:"+ user_nearname,Toast.LENGTH_SHORT).show();

                Looper.loop();

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","更新失败");
                Toast.makeText(InfoSettings.this, "更新失败", Toast.LENGTH_LONG).show();
                Looper.loop();

            }
        }
    };

    public void prev(View view){
        Intent in=getIntent();
        //设置返回结果成功
        setResult(RESULT_OK, in);
        //关闭当前activity
        finish();
    }


}
