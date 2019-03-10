package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.User;
import com.example.feichai.myapplication.SQLiteDataBase.UserService;
import com.mob.MobSDK;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;


public class Find_password extends Activity{

    private EditText find_user_name,code;
    private Context context;
    Button find_app_sign_button;
    private TimeCountUtil timeCountUtil;
    EventHandler eventHandle;
    String pn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password);

        ActivityManager.getInstance().addActivity(this);

        find_user_name = (EditText)findViewById(R.id.find_user_name);


        find_app_sign_button = (Button)findViewById(R.id.find_app_sign_button);
        context = getApplicationContext();

        //设置倒数秒数
        //btn_getCord这个是点击获取验证码的按钮
        timeCountUtil = new TimeCountUtil(find_app_sign_button, 10000, 1000);//这里的倒计时的时间 是 ：用第二参数 / 第三个三参数 = 倒计时为10秒

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
            case R.id.find_app_sign_button:
//                if (judPhone()) {
//                    timeCountUtil.start();
//                    pn = find_user_name.getText().toString();
//                    Toast.makeText(context,"发送",Toast.LENGTH_LONG).show();
//                    SMSSDK.getVerificationCode("86",pn);
//                    SMSSDK.setAskPermisionOnReadContact(true);
//                    Toast.makeText(context,"将对您"+pn+"的手机号码进行智能验证",Toast.LENGTH_LONG).show();
//                }
                Intent intent = new Intent(Find_password.this, MainActivity_Out.class);
                Find_password.this.startActivity(intent);
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

            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                if (result == SMSSDK.RESULT_COMPLETE){
                    boolean mobcheck = (Boolean) data;
                    if (mobcheck){
                        Toast.makeText(context,"智能验证成功",Toast.LENGTH_LONG).show();
                        Toast.makeText(Find_password.this, "登陆成功", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Find_password.this, MainActivity.class);
                        Find_password.this.startActivity(intent);
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

    private boolean judPhone()
    {
        if(TextUtils.isEmpty(find_user_name.getText().toString().trim()))
        {
            Toast.makeText(Find_password.this,"请输入您的电话号码",Toast.LENGTH_LONG).show();
            find_user_name.requestFocus();
            return false;
        }
        else if(find_user_name.getText().toString().trim().length()!=11)
        {
            Toast.makeText(Find_password.this,"您的电话号码位数不正确",Toast.LENGTH_LONG).show();
            find_user_name.requestFocus();
            return false;
        }
        else
        {
            pn=find_user_name.getText().toString().trim();
            String num="[1][3578]\\d{9}";
            if(pn.matches(num))
                return true;
            else
            {
                Toast.makeText(Find_password.this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }



    //用户名输入框图标调整
    protected  void onStart(){
        super.onStart();
        setDrawbleLeft(find_user_name,R.drawable.user_head);

    }

    private void setDrawbleLeft(EditText editText,int res){
        Drawable drawable=getResources().getDrawable(res);
        drawable.setBounds(0,0,55,55);
        editText.setCompoundDrawables(drawable,null,null,null);
    }

}
