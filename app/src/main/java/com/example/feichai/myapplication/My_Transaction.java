package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class My_Transaction extends Activity {

    private ListView listView;
    private My_Transaction_Item_Adapter my_transaction_item_adapter;
    private List<My_Transaction_info_Item> list = new ArrayList<>();
    Timestamp bill_time;
    String goods_name,user_name;
    int bill_money;
    LoadingDialog dialog;
    Context cxt;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_my_transaction);

        ActivityManager.getInstance().addActivity(this);

        Intent intent = this.getIntent();
        user_name = intent.getStringExtra("name");

        cxt = this;
        dialog = new LoadingDialog(cxt);


        listView = (ListView)findViewById(R.id.my_transaction_listView);
        my_transaction_item_adapter = new My_Transaction_Item_Adapter(this,list);
        listView.setAdapter(my_transaction_item_adapter);

        new Thread(runnable).start();

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //dialog.show();
            try{

                System.out.println("进入用户交易查询");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("连接成功");

                Statement stmt = con.createStatement();
                String sql = "select * from bill where user_id = "+user_name;
                ResultSet rs = stmt.executeQuery(sql);

                //int col = rs.getMetaData().getColumnCount();

                while (rs.next() == true)
                {
                    //for (int i=1;i<=col;i++){
                    bill_money = rs.getInt("bill_money");
                    bill_time = rs.getTimestamp("bill_start_time");
                    goods_name = rs.getString("goods");

                    list.add(new My_Transaction_info_Item(goods_name,String.valueOf(bill_money),bill_time));

                    System.out.println("长度："+list.size());

                    Log.i("TAG","查询订单成功");
                    //}
                    //Toast.makeText(MainActivity_Out.this, "登录成功", Toast.LENGTH_LONG).show();

                }
                if(my_transaction_item_adapter != null){
                    handler.post(new Runnable(){
                        @Override
                        public void run() {
                            my_transaction_item_adapter.notifyDataSetChanged();
                        }
                    });
                }
                //dialog.dismiss();
                rs.close();
                stmt.close();
            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","获取交易列表失败");
                Toast.makeText(My_Transaction.this, "获取交易列表失败", Toast.LENGTH_LONG).show();
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
