package com.example.feichai.myapplication;

import android.app.Activity;

import java.sql.*;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.SQLiteDataBase.Energy_Tree;
import com.wang.avi.AVLoadingIndicatorView;

import java.sql.Connection;
import java.sql.SQLException;

public class MainActivity_Out extends Activity {

    EditText main_user_name;
    EditText main_user_password;
    TextView register_tv;
    TextView find_tv;
    Button sign_button;
    String pass,manage_name,nearname;
    public String name;
    Energy_Tree energy_tree = new Energy_Tree();
    LoadingDialog dialog;
    Context cxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_out);
       // new DatabaseHelper(MainActivity_Out.this);

        cxt = this;

        ActivityManager.getInstance().addActivity(this);

        findViews();

        dialog = new LoadingDialog(cxt);
//        avLoadingIndicatorView =

        //仅点击外部不可取消
        dialog.setCanceledOnTouchOutside(false);

        //输入框图标调整
        main_user_name=(EditText)findViewById(R.id.main_out_user_name);
        main_user_password=(EditText)findViewById(R.id.main_out_user_password);

        //设置找回密码点击事件
        find_tv=(TextView) this.findViewById(R.id.main_out_find_password);

        find_tv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity_Out.this,Find_password.class);
                MainActivity_Out.this.startActivity(intent);
            }

        });
        //设置下划线
        find_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        find_tv.getPaint().setAntiAlias(true);//抗锯齿

        //设置注册点击事件
        register_tv=(TextView) this.findViewById(R.id.main_out_register_button);

        register_tv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity_Out.this,Register.class);
                MainActivity_Out.this.startActivity(intent);
            }

        });
        //设置下划线
        register_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        register_tv.getPaint().setAntiAlias(true);//抗锯齿

    }

    //输入框图标调整
    protected  void onStart(){
        super.onStart();
        setDrawbleLeft(main_user_name,R.drawable.user_head);
        setDrawbleLeft(main_user_password,R.drawable.lock);
    }

    private void setDrawbleLeft(EditText editText,int res){
        Drawable drawable=getResources().getDrawable(res);
        drawable.setBounds(0,0,50,50);
        editText.setCompoundDrawables(drawable,null,null,null);
    }


    //登录设置
    private void findViews() {
        main_user_name=(EditText) findViewById(R.id.main_out_user_name);
        main_user_password=(EditText) findViewById(R.id.main_out_user_password);
        sign_button=(Button) findViewById(R.id.main_out_app_sign_button);
        sign_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dialog.show();

                name=main_user_name.getText().toString();
                pass=main_user_password.getText().toString();
                Log.i("TAG",name+"_"+pass);

                System.out.println("登陆查询");

                if(name.equals("13083667903")|name.equals("15638196693")|name.equals("15290015465"))
                {
                    System.out.println("管理员");
                    if (pass.equals("123") ){
                        Intent intent=new Intent(MainActivity_Out.this,MainActivity.class);
                        intent.putExtra("name",name);
                        MainActivity_Out.this.startActivity(intent);
                    }
                }
                else {
                    System.out.println("用户");
                    new Thread(runnable).start();
                }
            }
        });

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try{
                System.out.println("进入");
                String DRIVER_NAME = "com.mysql.jdbc.Driver";
                Connection con = null;
                Class.forName(DRIVER_NAME);
                System.out.println(DRIVER_NAME);
                con = DriverManager.getConnection("jdbc:mysql://47.107.128.232/feichai?autoReconnect=true", "root", "123456");
                System.out.println("连接成功");


//                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("进入");

                Statement stmt = con.createStatement();
                String sql = "select * from user where user_name = "+name+" and password = "+pass;
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next() == true)
                {
                    manage_name = rs.getString("manage_id");
                    nearname = rs.getString("user_nearname");
                    dialog.dismiss();
                    rs.close();
                    stmt.close();
                    Looper.prepare();
                    Toast.makeText(MainActivity_Out.this, "用户"+name+"登陆成功", Toast.LENGTH_LONG).show();

                    Log.i("TAG","登录成功");

                    Intent intent=new Intent(MainActivity_Out.this,MainActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("manage_name",manage_name);
                    intent.putExtra("nearname",nearname);
                    MainActivity_Out.this.startActivity(intent);
                    Looper.loop();

                }
            }catch (SQLException e)
            {
                dialog.dismiss();
                System.out.println(e.getMessage());
                Log.i("TAG","登录失败");
                Looper.prepare();
                Toast.makeText(MainActivity_Out.this, "登录失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            try{
                System.out.println("进入");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("进入");

                Statement stmt = con.createStatement();
                String sql = "select * from manager where managername = "+name+" and password = "+pass;
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next() == true)
                {

                    dialog.dismiss();
                    rs.close();
                    stmt.close();
                    Looper.prepare();
                    Toast.makeText(MainActivity_Out.this, "用户"+name+"登陆成功", Toast.LENGTH_LONG).show();

                    Log.i("TAG","登录成功");

                    Intent intent=new Intent(MainActivity_Out.this,MainActivity.class);
                    intent.putExtra("name",name);
                    MainActivity_Out.this.startActivity(intent);
                    Looper.loop();

                }
            }catch (SQLException e)
            {
                dialog.dismiss();
                Log.i("TAG","登录失败");
                Looper.prepare();
                Toast.makeText(MainActivity_Out.this, "登录失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };

}
