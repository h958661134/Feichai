package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Looper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.SQLiteDataBase.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Info_Find_password extends Activity {

    EditText pass;
    String text_pass,user_name;
    User user = new User();
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_find_password);


        ActivityManager.getInstance().addActivity(this);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");

        pass = (EditText)findViewById(R.id.find_password);

        button = (Button)findViewById(R.id.button_pass) ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_pass = pass.getText().toString().trim();
                user.setPassword(text_pass);
                new Thread(runnable).start();
            }
        });


    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            System.out.println("进入密码更新");
            Connection con = DatabaseHelper.getCon("root","123456");
            System.out.println(con);
            System.out.println("进入更新");

            String sql = "update user set password = "+user.getPassword()+" where user_name = "+user_name;
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
                Toast.makeText(Info_Find_password.this, "更新成功", Toast.LENGTH_LONG).show();
                Looper.loop();

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","更新失败");
                Toast.makeText(Info_Find_password.this, "更新失败", Toast.LENGTH_LONG).show();
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
