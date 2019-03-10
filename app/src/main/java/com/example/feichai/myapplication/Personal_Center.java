package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.SQLiteDataBase.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Personal_Center extends Activity {

    String user_name,email,password,nearname,manage_id;
    int which;
    TextView personal_user_name,personal_user_email;
    int find_count,find_need,find_level,find_percent;
    Boolean is_can = false;
    LoadingDialog dialog;
    Context cxt;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);


        ActivityManager.getInstance().addActivity(this);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        nearname = intent.getStringExtra("nearname");
        manage_id =intent.getStringExtra("manage_name");
        System.out.println("用户"+user_name+"进入personal");

        cxt = this;
        dialog = new LoadingDialog(cxt);

        new Thread(runnable).start();

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try{
                System.out.println("进入用户主页");
                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("进入");

                Statement stmt = con.createStatement();
                String sql = "select * from user where user_name = "+user_name;
                ResultSet rs = stmt.executeQuery(sql);

                while(rs.next())
                {
                    password = rs.getString("password");
                    email = rs.getString("email");

                    rs.close();
                    stmt.close();
                    Looper.prepare();
                    //Toast.makeText(Personal_Center.this, "用户"+user_name+"登陆成功", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Personal_Center.this, "密码"+password+"  昵称 "+nearname, Toast.LENGTH_LONG).show();

                    Log.i("TAG","查询成功");
                    //Toast.makeText(MainActivity_Out.this, "登录成功", Toast.LENGTH_LONG).show();
                    Looper.loop();
                    set_TextView();

                }
            }catch (SQLException e)
            {
                Log.i("TAG","查询失败");
                Toast.makeText(Personal_Center.this, "查询失败", Toast.LENGTH_LONG).show();
            }
        }
    };


    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {

            try{
                System.out.println("进入Tree查询");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);

                String sql = "select * from energy_tree where user_id = "+user_name;
                System.out.println("Tree连接成功");
                PreparedStatement pstmt;
                pstmt = (PreparedStatement)con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                Log.i("TAG","将要进入Tree");

                if (rs.next() == true)
                {
                    dialog.dismiss();
                    Log.i("TAG","进入Tree成功");

                    find_count = rs.getInt(2);
                    find_level = rs.getInt(3);
                    find_need = rs.getInt(4);
                    find_percent = rs.getInt(5);

                    is_can = true;
                    System.out.println("count: "+find_count);
                    System.out.println("count: "+find_count);

                    Log.i("TAG","查询Tree成功");

                }

                rs.close();
                pstmt.close();

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","获取Tree失败");
                Toast.makeText(Personal_Center.this, "获取Tree失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };


    public void set_TextView(){
        personal_user_email = (TextView)findViewById(R.id.personal_user_mailbox);
        personal_user_name = (TextView)findViewById(R.id.personal_user_name);
        personal_user_name.setText(nearname);
        personal_user_email.setText(email);
    }


    public void skip_to_setting(View view){
        Intent intent = new Intent(Personal_Center.this,Setting.class);
        intent.putExtra("user_name",user_name);
        intent.putExtra("password",password);
        intent.putExtra("email",email);
        intent.putExtra("nearname",nearname);
        Personal_Center.this.startActivity(intent);
    }

    public void skip_to_next(View view){

        switch (view.getId()){
            case R.id.personal_my_application_linearLayout:
                Intent intent1 = new Intent(Personal_Center.this,My_Application.class);
                intent1.putExtra("name",user_name);
                Personal_Center.this.startActivity(intent1);
                break;

            case R.id.personal_transaction_linearLayout:
                Intent intent2 = new Intent(Personal_Center.this,My_Transaction.class);
                intent2.putExtra("name",user_name);
                Personal_Center.this.startActivity(intent2);
                break;

            case R.id.personal_footprint_linearLayout:
                Intent intent3 = new Intent(Personal_Center.this,My_Footprint.class);
                intent3.putExtra("name",user_name);
                intent3.putExtra("nearname",nearname);
                intent3.putExtra("manage_id",manage_id);
                Personal_Center.this.startActivity(intent3);
                break;

            case R.id.personal_after_sale_linearLayout:
                Intent intent4 = new Intent(Personal_Center.this,ChatActivity.class);
                intent4.putExtra("name",user_name);
                intent4.putExtra("near_name",nearname);
                intent4.putExtra("manage_id",manage_id);
                Personal_Center.this.startActivity(intent4);
                break;

            case R.id.personal_energy_tree_linearLayout:
                dialog.show();
                new Thread(runnable1).start();
                if(is_can == true) {
                    Intent intent5 = new Intent(Personal_Center.this, My_Energy_Tree.class);
                    intent5.putExtra("name", user_name);
                    intent5.putExtra("percent", find_percent);
                    intent5.putExtra("count", find_count);
                    intent5.putExtra("need", find_need);
                    intent5.putExtra("level", find_level);
                    Personal_Center.this.startActivity(intent5);
                }
                break;

            case R.id.personal_favourite_linearLayout:
                Intent intent6 = new Intent(Personal_Center.this,About_us.class);
                Personal_Center.this.startActivity(intent6);
                break;

            case R.id.personal_call_linearLayout:
                Intent intent7 = new Intent(Personal_Center.this,Call_us.class);
                Personal_Center.this.startActivity(intent7);
                break;


            default:
                break;
        }


    }

    public void skip_to_activity(View view){

        switch (view.getId()){

            case R.id.main:
                Intent intent1 = new Intent(Personal_Center.this,MainActivity.class);
                intent1.putExtra("name",user_name);
                intent1.putExtra("manage_name",manage_id);
                intent1.putExtra("nearname",nearname);
                Personal_Center.this.startActivity(intent1);
                break;
            case R.id.classify:
                Intent intent2 = new Intent(Personal_Center.this,ClassifyActivity.class);
                intent2.putExtra("name",user_name);
                intent2.putExtra("manage_name",manage_id);
                intent2.putExtra("nearname",nearname);
                Personal_Center.this.startActivity(intent2);
                break;
            case R.id.trade:
                Intent intent3 = new Intent(Personal_Center.this,Trade_Activity.class);
                intent3.putExtra("name",user_name);
                intent3.putExtra("manage_name",manage_id);
                intent3.putExtra("nearname",nearname);
                intent3.putExtra("from",0);
                Personal_Center.this.startActivity(intent3);
                break;
            case R.id.map:
                Intent intent4 = new Intent(Personal_Center.this,MapActivity.class);
                intent4.putExtra("name",user_name);
                intent4.putExtra("manage_name",manage_id);
                intent4.putExtra("nearname",nearname);
                Personal_Center.this.startActivity(intent4);
                break;

            default:break;

        }

    }



}
