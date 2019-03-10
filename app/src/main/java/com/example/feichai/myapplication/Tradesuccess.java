package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Tradesuccess extends Activity {
    String user_name,manage_id,nearname;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tradesuccess);

        ActivityManager.getInstance().addActivity(this);

        Intent intent =getIntent();
        user_name = intent.getStringExtra("name");
        nearname = intent.getStringExtra("nearname");
        manage_id =intent.getStringExtra("manage_name");

        Button bt_back;
        bt_back = (Button)findViewById(R.id.btn_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tradesuccess.this,Trade_Activity.class);
                intent.putExtra("name",user_name);
                intent.putExtra("manage_name",manage_id);
                intent.putExtra("nearname",nearname);
                Tradesuccess.this.startActivity(intent);
            }
        });

    }

}
