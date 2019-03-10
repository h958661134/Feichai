package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class My_Footprint extends Activity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private My_Footprint_Item_Adapter my_footprint_item_adapter;
    private List<My_Footprint_info_Item> list = new ArrayList<>();
    String user_name,user_nearname,sel_user_name,manage_id,nearname;
    String manage_name,manage_nearname;
    private Handler handler=new Handler();
    ImageView go_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_my_footprint);

        Intent intent = this.getIntent();
        user_name = intent.getStringExtra("name");
        manage_id = intent.getStringExtra("namage_id");
        nearname = intent.getStringExtra("nearname");

        ActivityManager.getInstance().addActivity(this);

        //initItems();
        listView = (ListView)findViewById(R.id.my_footprint_listView);
        my_footprint_item_adapter = new My_Footprint_Item_Adapter(this,list);
        listView.setAdapter(my_footprint_item_adapter);

        go_next = (ImageView)findViewById(R.id.my_footprint_item_next);

        if(user_name.equals("13083667903")||user_name.equals("15638196693")||user_name.equals("15290015465"))
        {
            new Thread(runnable2).start();
        }
        else {
            new Thread(runnable).start();
        }

        listView.setOnItemClickListener(this);

    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //dialog.show();
            try{

                System.out.println("进入用户足迹查询");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("连接成功");

                Statement stmt = con.createStatement();
                String sql = "select manage_id from commit where user_id = "+user_name;
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next() == true)
                {
                    manage_name = rs.getString("manage_id");
                    list.add(new My_Footprint_info_Item(manage_name,new Intent(My_Footprint.this,ChatActivity.class)));
                    System.out.println("长度："+list.size());


                    Log.i("TAG","查询足迹成功");
                    rs.close();
                    stmt.close();
                }
                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        my_footprint_item_adapter.notifyDataSetChanged();
                    }
                });

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","获取足迹失败");
                Toast.makeText(My_Footprint.this, "获取足迹失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };

    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            //dialog.show();
            try{

                System.out.println("进入管理员足迹查询");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("连接成功");

                Statement stmt = con.createStatement();
                String sql = "select distinct user_id from commit where manage_id = '"+manage_nearname+"'";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next() == true)
                {
                    sel_user_name = rs.getString("user_id");
                    list.add(new My_Footprint_info_Item(sel_user_name,new Intent(My_Footprint.this,ChatActivity.class)));
                    System.out.println("长度："+list.size());
                    Log.i("TAG","查询足迹成功");
                }
                //dialog.dismiss();


                rs.close();
                stmt.close();

                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        my_footprint_item_adapter.notifyDataSetChanged();
                    }
                });

            }catch (SQLException e)
            {
                e.printStackTrace();
                Looper.prepare();
                Log.i("TAG","获取交易列表失败");
                Toast.makeText(My_Footprint.this, "获取足迹失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            //dialog.show();
            try{

                System.out.println("进入管理员昵称查询");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("连接成功");

                Statement stmt = con.createStatement();
                String sql = "select name from manager where managername = "+user_name;
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next() == true)
                {
                    manage_nearname = rs.getString("name");

                    System.out.println(manage_nearname);

                    Log.i("TAG","查询昵称成功");
                    rs.close();
                    stmt.close();
                    new Thread(runnable1).start();
                }

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","获取足迹失败");
                Toast.makeText(My_Footprint.this, "获取足迹失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };



    public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {

        list.get(i).getIntent().putExtra("name",user_name);
        list.get(i).getIntent().putExtra("which","");
        list.get(i).getIntent().putExtra("near_name",nearname);
        list.get(i).getIntent().putExtra("manage_id",manage_nearname);
        list.get(i).getIntent().putExtra("phone",list.get(i).getFootprint_company_name());
        My_Footprint.this.startActivity(list.get(i).getIntent());

    }


    public void prev(View view){
        Intent in=getIntent();
        //设置返回结果成功
        setResult(RESULT_OK, in);
        //关闭当前activity
        finish();
    }


}
