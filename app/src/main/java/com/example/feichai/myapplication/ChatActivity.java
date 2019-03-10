package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class ChatActivity extends Activity implements View.OnClickListener {
    private ImageView mBtnSend;
    private ImageView mBtnBack;
    private EditText mEditTextContent;
    //聊天内容的适配器
    private ChatAdapter mAdapter;
    private ListView mListView;
    //聊天的内容
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
    private TextView myorder;
    public String service;
    private String consum="我";
    public TextView map,menu;
    private TextView order_consult;
    StringBuffer sbBuffer = new StringBuffer();
    String content="";
    private Handler handler=new Handler();
    private String data;
    private String time;
    String user_name,manage_name,user_nearname;
    public String phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_conversation);

        ActivityManager.getInstance().addActivity(this);

        //判断是否有内容输入搜索框
        Intent intent=getIntent();
        String search=intent.getStringExtra("search");
        user_name=intent.getStringExtra("name");
        user_nearname = intent.getStringExtra("near_name");
        manage_name = intent.getStringExtra("manage_id");

        System.out.println("用户"+user_name+"进入chatActivity");
        System.out.println("nicheng"+user_nearname+"进入chatActivity");


            if (search == null || search.equals("")) {
                service = manage_name + "号";
            } else {
                service = search;
            }


        initView();
        initData();

        map = (TextView)findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name",user_name);
                intent.putExtra("manage_name",manage_name);
                intent.putExtra("nearname",user_nearname);
                intent.setClass(ChatActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });

        menu = (TextView)findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name",user_name);
                intent.putExtra("manage_name",manage_name);
                intent.putExtra("nearname",user_nearname);
                intent.setClass(ChatActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        myorder=(TextView)findViewById(R.id.order_consult);
        myorder.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name",user_name);
                intent.putExtra("manage_name",manage_name);
                intent.putExtra("nearname",user_nearname);
                intent.setClass(ChatActivity.this,My_Transaction.class);
                startActivity(intent);
            }
        });

        order_consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("name",user_name);
                intent1.putExtra("manage_name",manage_name);
                intent1.putExtra("nearname",user_nearname);
                intent1.setClass(ChatActivity.this,My_Transaction.class);
                startActivity(intent1);
            }
        });


    }


    //初始化化视图
    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mBtnBack = (ImageView) findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        mBtnSend = (ImageView) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
        order_consult=(TextView)findViewById(R.id.order_consult);
    }




    //初始化要显示的数据
    private void initData() {

        mAdapter = new ChatAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);
        if(user_name.equals("13083667903")|user_name.equals("15638196693")|user_name.equals("15290015465"))
        {
            Intent intent=getIntent();
            phone = intent.getStringExtra("phone");
            System.out.println("管理员");
            new Thread(runmanager).start();
        }
        else {
            System.out.println("用户");
            new Thread(selectrunnable).start();
        }

    }

    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch(view.getId()) {
            case R.id.btn_back:
                back();
                break;
            case R.id.btn_send:
                send();
                break;
        }
    }

    private void send()
    {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() > 0)
        {
            content=contString;
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(getDate());
            entity.setName(consum);
            entity.setMsgType(false);
            entity.setText(contString);
            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();
            mEditTextContent.setText("");
            mListView.setSelection(mListView.getCount() - 1);
            if(user_name.equals("13083667903")|user_name.equals("15638196693")|user_name.equals("15290015465"))
            {
                new Thread(runnable2).start();
            }
            else {
                new Thread(runnable1).start();
            }

        }
    }

    //获取日期
    public String getDate() {
        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH)+ 1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH)) ;
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));
        String second = String.valueOf(c.get(Calendar.SECOND));

        data=year+"-"+month+"-"+day;
        time=hour+":"+mins+":"+second;

        if(Integer.parseInt(mins)<10 ) {
            if (Integer.parseInt(second) < 10)
            { sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":0" + mins + ":0" + second); }
            else { sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":0" + mins +":" +second); }
        }
        else {
            if (Integer.parseInt(second) < 10) {
                sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"+mins + ":0" + second);
            } else {
                sbBuffer.append(year + "-" + month + "-" + day + " " + hour +":"+ mins +":"+ second);
            }
        }
        return sbBuffer.toString();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        back();
        return true;
    }

    private void back() {
        finish();
    }
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            try{
                Connection con = DatabaseHelper.getCon("root","123456");
                //生成id号
                UUID uuid=UUID.randomUUID();
                String str=uuid.toString();
                String sql = "insert into commit values(?,?,?,?,?,?,?,?);";
                PreparedStatement pstmt = con.prepareStatement(sql);

                System.out.println(user_name);

                pstmt.setString(1,str);
                pstmt.setString(2,user_name);
                pstmt.setString(3,manage_name);
                pstmt.setString(4,data);
                pstmt.setString(5,time);
                pstmt.setString(6,content);
                pstmt.setString(7,user_nearname);
                pstmt.setInt(8,1);
                //对缓冲区清空
                sbBuffer.setLength(0);
                System.out.println("nicheng"+user_nearname+"进入chatActivity");
                pstmt.executeUpdate();

                pstmt.close();
                con.close();
                Looper.prepare();
                //Toast.makeText(ChatActivity.this, "聊天记录输入成功", Toast.LENGTH_LONG).show();
                Looper.loop();

            }catch (SQLException e)
            {
                e.printStackTrace();
                Looper.prepare();
                Log.i("TAG","聊天数据添加失败");
                Toast.makeText(ChatActivity.this, "聊天数据添加失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            try{
                Connection con = DatabaseHelper.getCon("root","123456");
                //生成id号
                UUID uuid=UUID.randomUUID();
                String str=uuid.toString();
                String sql = "insert into commit values(?,?,?,?,?,?,?,?);";
                PreparedStatement pstmt = con.prepareStatement(sql);

                System.out.println(user_name);

                pstmt.setString(1,str);
                pstmt.setString(2,phone);
                pstmt.setString(3,user_name);
                pstmt.setString(4,data);
                pstmt.setString(5,time);
                pstmt.setString(6,content);
                pstmt.setString(7,"用户");
                pstmt.setInt(8,2);
                //对缓冲区清空
                sbBuffer.setLength(0);
                System.out.println("nicheng"+user_nearname+"进入chatActivity");
                pstmt.executeUpdate();

                pstmt.close();
                con.close();
                Looper.prepare();
                //Toast.makeText(ChatActivity.this, "聊天记录输入成功", Toast.LENGTH_LONG).show();
                Looper.loop();

            }catch (SQLException e)
            {
                e.printStackTrace();
                Looper.prepare();
                Log.i("TAG","聊天数据添加失败");
                Toast.makeText(ChatActivity.this, "聊天数据添加失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };

    /**
     * 用于数据库查询
     * */
    Runnable selectrunnable = new Runnable() {
        @Override
        public void run() {
            try{
                Connection con = DatabaseHelper.getCon("root","123456");
                //从数据库中查询
                String selectsql="select * from commit where user_id="+user_name;
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery(selectsql);
                while(rs.next())
                {
                    ChatMsgEntity entity = new ChatMsgEntity();
                    String data="",time="";
                    data=rs.getString("com_date");
                    entity.setText(rs.getString("com_content"));
                    time=rs.getString("com_time");
                    String datatime=data+" "+time;
                    System.out.println(datatime);
                    entity.setDate(datatime);
                    if(rs.getInt("sign")==1) {
                        entity.setName(consum);
                        entity.setMsgType(false);
                    }else{
                        entity.setMsgType(true);
                        entity.setName(service);
                    }
                    mDataArrays.add(entity);
                    mAdapter = new ChatAdapter(ChatActivity.this, mDataArrays);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //mListview仅有父进程可以使用,需要使用handler
                            mListView.setAdapter(mAdapter);
                            mListView.setSelection(mListView.getCount() - 1);
                        }
                    });
                }
                System.out.println("------------");
                stmt.close();
                con.close();
                Looper.prepare();
                //Toast.makeText(ChatActivity.this, "聊天记录获取成功", Toast.LENGTH_LONG).show();
                Looper.loop();

            }catch (SQLException e)
            {
                e.printStackTrace();
                Looper.prepare();
                Log.i("TAG","聊天数据获取失败");
                Toast.makeText(ChatActivity.this, "聊天数据获取失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };

    Runnable runmanager = new Runnable() {
        @Override
        public void run() {
            try{
                Connection con = DatabaseHelper.getCon("root","123456");
                //从数据库中查询
                String selectsql="select * from commit where user_id="+phone;
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery(selectsql);
                while(rs.next())
                {
                    ChatMsgEntity entity = new ChatMsgEntity();
                    String data="",time="";
                    data=rs.getString("com_date");
                    entity.setText(rs.getString("com_content"));
                    time=rs.getString("com_time");
                    String datatime=data+" "+time;
                    System.out.println(datatime);
                    entity.setDate(datatime);
                    if(rs.getInt("sign")==1) {
                        entity.setName(phone);
                        entity.setMsgType(true);
                    }else{
                        entity.setMsgType(false);
                        entity.setName(consum);
                    }
                    mDataArrays.add(entity);
                    mAdapter = new ChatAdapter(ChatActivity.this, mDataArrays);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //mListview仅有父进程可以使用,需要使用handler
                            mListView.setAdapter(mAdapter);
                            mListView.setSelection(mListView.getCount() - 1);
                        }
                    });
                }
                System.out.println("------------");
                stmt.close();
                con.close();
                Looper.prepare();
                //Toast.makeText(ChatActivity.this, "聊天记录获取成功", Toast.LENGTH_LONG).show();
                Looper.loop();

            }catch (SQLException e)
            {
                e.printStackTrace();
                Looper.prepare();
                Log.i("TAG","聊天数据获取失败");
                Toast.makeText(ChatActivity.this, "聊天数据获取失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };

}
