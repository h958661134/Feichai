package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackSettings extends Activity {

    private EditText feedbackET;
    private EditText feedbackNumET;
    private Button feedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_feedback);

        ActivityManager.getInstance().addActivity(this);

        feedbackET = (EditText) findViewById(R.id.feedbackET);
        feedbackNumET = (EditText) findViewById(R.id.feedbackNumET);
        feedbackButton = (Button) findViewById(R.id.feedbackButton);

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FeedbackSettings.this, "意见：" + feedbackET.getText() + "\n" + "手机号/邮箱：" + feedbackNumET.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void prev(View view){
        Intent in=getIntent();
        //设置返回结果成功
        setResult(RESULT_OK, in);
        //关闭当前activity
        finish();
    }

}
