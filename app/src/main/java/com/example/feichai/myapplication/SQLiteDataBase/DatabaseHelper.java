package com.example.feichai.myapplication.SQLiteDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.content.ContentValues.TAG;


public class DatabaseHelper extends SQLiteOpenHelper {

    static String name = "user.db";
    static int dbVersion = 1;
    //Connection connection = null;

    public DatabaseHelper(Context context) {
        super(context, name, null, dbVersion);
    }

    //只在创建的时候用一次
    public void onCreate(SQLiteDatabase db) {
//        String sql="create table user(id integer primary key autoincrement,user_name varchar(20),password varchar(20),email varchar(20),user_nearname varchar(20),sex vatchar(4),user_pic integer)";
//        db.execSQL(sql);
        Log.i("TAG", "成功1");


    }

    public static Connection getCon(String user, String password) {
        Connection conn = null;
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection("jdbc:mysql://47.107.128.232/feichai?useUnicode=true&amp;characterEncoding=utf-8", user, password);
            System.out.println("连接成功");
        } catch (ClassNotFoundException e) {
            System.out.println("888");
            conn = null;
        } catch (SQLException e) {
            System.out.println(777);
            conn = null;
        }

        return conn;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = new Bundle();
            data = msg.getData();
            System.out.println("username" + data.get("username").toString());
            System.out.println("userpass" + data.get("userpass").toString());
        }
    };

}





