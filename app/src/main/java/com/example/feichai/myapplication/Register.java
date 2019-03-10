package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.RegionIterator;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.SQLiteDataBase.Energy_Tree;
import com.example.feichai.myapplication.SQLiteDataBase.User;
import com.example.feichai.myapplication.SQLiteDataBase.UserService;
import com.mob.MobSDK;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class Register extends Activity {
    private EditText register_user_name,code,register_password,register_address;
    private Context context;
    Button register_getcode_button,register_app_sign_button;
    private TimeCountUtil timeCountUtil;
    EventHandler eventHandle;
    String pn,cn;
    String name,password,address;
    User user  = new User();
    Energy_Tree energy_tree = new Energy_Tree();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ActivityManager.getInstance().addActivity(this);

        register_user_name = (EditText)findViewById(R.id.register_user_name);
        register_address = (EditText)findViewById(R.id.register_address);
        register_password = (EditText)findViewById(R.id.register_user_password);
        register_getcode_button = (Button)findViewById(R.id.register_phone_getcode);
        register_app_sign_button = (Button)findViewById(R.id.register_app_sign_button);
        context = getApplicationContext();
        code = (EditText)findViewById(R.id.register_phone_code);


        //设置倒数秒数
        //btn_getCord这个是点击获取验证码的按钮
        timeCountUtil = new TimeCountUtil(register_getcode_button, 10000, 1000);//这里的倒计时的时间 是 ：用第二参数 / 第三个三参数 = 倒计时为10秒

        MobSDK.init(context,"271bd74325419","2c06cfed3c64190080752ec4891d7ed5");

        EventHandler eventHandler = new EventHandler(){
            public void afterEvent(int event,int result, Object data){
                super.afterEvent(event,result,data);
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mhandle.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    public void onClick_sign(View view){
        switch (view.getId()){
            case R.id.register_phone_getcode:
                if (judPhone()) {
                    timeCountUtil.start();

                    pn = register_user_name.getText().toString();
                    Toast.makeText(context,"发送",Toast.LENGTH_LONG).show();
                    SMSSDK.getVerificationCode("86",pn);
                    SMSSDK.setAskPermisionOnReadContact(true);
                    Toast.makeText(context,"验证码即将发送到您"+pn+"的手机上",Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.register_app_sign_button:
               if (judCord()){
                   SMSSDK.submitVerificationCode("86",pn,code.getText().toString());
                   Toast.makeText(context,"正在提交验证",Toast.LENGTH_LONG).show();
                   SMSSDK.getSupportedCountries();

               }

                break;

               default:break;
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandle);
    }


    Handler mhandle = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;

            if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                if(result == SMSSDK.RESULT_COMPLETE){
                    Toast.makeText(context,"验证码验证成功"+result,Toast.LENGTH_LONG).show();

                    //存储注册信息
                    name = register_user_name.getText().toString().trim();
                    password = register_password.getText().toString().trim();
                    address = register_address.getText().toString().trim();

                    user.setUser_nearname(name);
                    user.setPassword(password);
                    user.setMail(address);
                    int i = (int)(Math.random()*2+1);
                    user.setManage_id("manager"+i);

                    energy_tree.setUser_name(name);
                    energy_tree.setEnergy_percent(0);
                    energy_tree.setEnergy_need(0);
                    energy_tree.setTree_level(0);
                    energy_tree.setEnergy_count(0);


                    Log.i("TAG", name + "_" + password + "_" + address);

                    new Thread(runnable).start();

                }else if (result == SMSSDK.RESULT_ERROR){
                    Toast.makeText(context,"验证码验证失败"+data,Toast.LENGTH_LONG).show();

                }
            }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                if (result == SMSSDK.RESULT_COMPLETE){
                    boolean mobcheck = (Boolean) data;
                    if (mobcheck){
                        Toast.makeText(context,"该手机号码已经注册过，请重新输入手机号",Toast.LENGTH_LONG).show();
                        register_user_name.requestFocus();
                        return;
                    }
                    else {
                        Toast.makeText(context,"短信验证",Toast.LENGTH_LONG).show();
                        Toast.makeText(context,"获取短信验证码成功",Toast.LENGTH_LONG).show();
                    }
                }else if (result == SMSSDK.RESULT_ERROR){
                    Toast.makeText(context,"获取短信验证码失败"+data,Toast.LENGTH_LONG).show();
                }
            }else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                SMSSDK.getSupportedCountries();
            }
            else {
                try {
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    JSONObject jsonObject = new JSONObject(throwable.getMessage());
                    String des = jsonObject.optString("datail");
                    int status = 0;
                    status = jsonObject.optInt("status");
                    if (TextUtils.isEmpty(des)){}
                }catch (Exception e){
                    SMSLog.getInstance().w(e);
                }
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

                System.out.println("进入注册");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("连接");

                String sql="insert into user(user_name,password,email,manage_id) values(?,?,?,?)";
                PreparedStatement pstmt;
                try{
                    pstmt = (PreparedStatement)con.prepareStatement(sql);
                    pstmt.setString(1,user.getUser_nearname());
                    pstmt.setString(2,user.getPassword());
                    pstmt.setString(3,user.getMail());
                    pstmt.setString(4,user.getManage_id());
                    int i=pstmt.executeUpdate();
                    System.out.println(i);
                    pstmt.close();
                    con.close();

                    Intent intent = new Intent(Register.this,MainActivity.class);
                    Register.this.startActivity(intent);

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","注册失败");
                Toast.makeText(Register.this, "注册失败", Toast.LENGTH_LONG).show();
                Looper.loop();
                e.printStackTrace();
            }
        }
    };

    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {

            System.out.println("进入Tree记录");

            Connection con = DatabaseHelper.getCon("root","123456");
            System.out.println(con);
            System.out.println("Tree连接");

            String sql="insert into energy_tree(user_id,energy_count,tree_level,energy_need,energy_percent) values(?,?,?,?,?)";
            PreparedStatement pstmt;
            try{
                System.out.println("Tree开始记录");
                pstmt = (PreparedStatement)con.prepareStatement(sql);
                pstmt.setString(1,energy_tree.getUser_name());
                pstmt.setInt(2,energy_tree.getEnergy_count());
                pstmt.setInt(3,energy_tree.getTree_level());
                pstmt.setInt(4,energy_tree.getEnergy_need());
                pstmt.setInt(5,energy_tree.getEnergy_percent());

                System.out.println("Tree记录完毕");
                int i=pstmt.executeUpdate();
                System.out.println(i);
                System.out.println("Tree更新");
                pstmt.close();
                con.close();
                System.out.println("Tree结束");

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","Tree记录失败");
                Toast.makeText(Register.this, "记录失败", Toast.LENGTH_LONG).show();
                Looper.loop();
                e.printStackTrace();
            }
        }
    };



    private boolean judPhone()
    {
        if(TextUtils.isEmpty(register_user_name.getText().toString().trim()))
        {
            Toast.makeText(Register.this,"请输入您的电话号码",Toast.LENGTH_LONG).show();
            register_user_name.requestFocus();
            return false;
        }
        else if(register_user_name.getText().toString().trim().length()!=11)
        {
            Toast.makeText(Register.this,"您的电话号码位数不正确",Toast.LENGTH_LONG).show();
            register_user_name.requestFocus();
            return false;
        }
        else
        {
            pn=register_user_name.getText().toString().trim();
            String num="[1][3578]\\d{9}";
            if(pn.matches(num))
                return true;
            else
            {
                Toast.makeText(Register.this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    private boolean judCord()
    {
        judPhone();
        if(TextUtils.isEmpty(code.getText().toString().trim()))
        {
            Toast.makeText(Register.this,"请输入您的验证码",Toast.LENGTH_LONG).show();
            code.requestFocus();
            return false;
        }
        else if(code.getText().toString().trim().length()!=4)
        {
            Toast.makeText(Register.this,"您的验证码位数不正确",Toast.LENGTH_LONG).show();
            code.requestFocus();

            return false;
        }
        else
        {
            cn=code.getText().toString().trim();
            return true;
        }

    }



    //用户名输入框图标调整
    protected  void onStart(){
        super.onStart();
        setDrawbleLeft(register_user_name,R.drawable.user_head);

    }

    private void setDrawbleLeft(EditText editText, int res){
        Drawable drawable=getResources().getDrawable(res);
        drawable.setBounds(0,0,50,50);
        editText.setCompoundDrawables(drawable,null,null,null);
    }



}
