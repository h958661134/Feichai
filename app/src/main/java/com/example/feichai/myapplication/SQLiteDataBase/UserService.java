package com.example.feichai.myapplication.SQLiteDataBase;

import java.sql.*;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.sql.PreparedStatement;

import com.example.feichai.myapplication.MainActivity;
import com.example.feichai.myapplication.R;
import com.example.feichai.myapplication.Register;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.content.ContentValues.TAG;

public class UserService {
    private DatabaseHelper dbHelper;
    private Connection connection,con;
    String name,password,email;
    public UserService(Context context){
        dbHelper=new DatabaseHelper(context);
    }
    public UserService(Connection connection){
        this.connection = connection;
        Log.i(TAG,"接收connect");
    }

    //注册用
    public boolean register(final User user){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("进入注册");

                    Connection con = DatabaseHelper.getCon("root","123456");
                    System.out.println(con);
                    System.out.println("连接");

                    Statement stmt = con.createStatement();
                    name = user.getUsername();
                    password = user.getPassword();
                    email = user.getMail();
                    String sql = "insert into user(user_name,password,email) values(?,?,?)";
                    ResultSet rs = stmt.executeQuery(sql);

                    if(rs.next() == true)
                    {
                        rs.close();
                        stmt.close();
                        Looper.prepare();

                        Log.i("TAG","登录成功");

                        Looper.loop();

                    }
                }catch (SQLException e)
                {
                    Log.i("TAG","登录失败");
                }
            }
        };

        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="insert into user(user_name,password,email) values(?,?,?)";
        Object obj[]={user.getUsername(),user.getPassword(),user.getMail()};
        sdb.execSQL(sql, obj);
        return true;
    }

    public boolean register1(User user){

        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="insert into user(user_name,password,email) values(?,?,?)";
        Object obj[]={user.getUsername(),user.getPassword(),user.getMail()};
        sdb.execSQL(sql, obj);
        return true;
    }


    public boolean set_nearname(String username,String near_name){
        SQLiteDatabase sdb=dbHelper.getWritableDatabase();
        String my_user_nearname = "update user set user_nearname = ? where user_name = ?";
        Object obj[]={near_name,username};
        sdb.execSQL(my_user_nearname,obj);
        return true;
    }

    public String get_nearname(String username){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String my_user_nearname = "select user_nearname from user where user_name = ?";
        Cursor cursor = sdb.rawQuery(my_user_nearname,new String[]{username});
        String ret_user_nearname = null;
        if(cursor.moveToFirst()){
            int position = cursor.getColumnIndex("user_nearname");
            ret_user_nearname = cursor.getString(position);
            if(ret_user_nearname == null){
                ret_user_nearname = "废废废废废柴";
            }
        }
        return ret_user_nearname;
    }

    public String get_email(String username){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String my_password = "select email from user where user_name = ?";
        Cursor cursor = sdb.rawQuery(my_password,new String[]{username});
        String ret_email = null;
        if(cursor.moveToFirst()){
        int position = cursor.getColumnIndex("email");
        ret_email = cursor.getString(position);
        }
        return ret_email;
    }


    public void set_user_pic(String username, int user_pic){
        SQLiteDatabase sdb=dbHelper.getWritableDatabase();
        String my_user_pic = "update user set user_pic = ? where user_name = ?";
        Object obj[]={user_pic,username};
        sdb.execSQL(my_user_pic,obj);

    }

    public int get_user_pic(String username){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String my_user_pic = "select user_pic from user where user_name = ?";
        Cursor cursor = sdb.rawQuery(my_user_pic,new String[]{username});
        int ret_user_pic = 0;
        if(cursor.moveToFirst()){
            int position = cursor.getColumnIndex("user_nearname");
            ret_user_pic = cursor.getInt(position);
            if(ret_user_pic == 0){
                ret_user_pic = R.drawable.head_sculpture;
            }
        }
        return ret_user_pic;
    }

}

