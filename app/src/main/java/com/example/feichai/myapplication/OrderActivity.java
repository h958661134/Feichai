package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class OrderActivity extends Activity{

    private ImageView reply;
    private ImageView search;
    private ListView lv_map;
    private SQLiteDatabase db;
    private BaseAdapter base;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActivityManager.getInstance().addActivity(this);

        //跳转主页面
        reply=(ImageView)findViewById(R.id.iv_mainlayout);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(OrderActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //跳转搜素页面
        search=(ImageView)findViewById(R.id.iv_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(OrderActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        //listview实现页面跳转
        lv_map=(ListView)findViewById(R.id.lv_map);
        lv_map.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderActivity.this,"click", Toast.LENGTH_SHORT).show();
                //实现页面跳转
            }
        });


        insertdata("实时订单");
        insertdata("卖出废旧金属");
        insertdata("卖出旧书籍");
        querydata();

    }

    public void insertdata(String data)
    {
        db=helper.getWritableDatabase();
        db.execSQL("insert into maps(name) values('"+data+"')");
        db.close();
    }

    public void querydata()
    {
        Cursor cursor=helper.getReadableDatabase().rawQuery("select id as _id,name from maps",null);
        base=new SimpleCursorAdapter(this, R.layout.orderlistview, cursor, new String[] { "name" },
                new int[] { R.id.map_tv}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        lv_map.setAdapter(base);
        base.notifyDataSetChanged();
    }
}
