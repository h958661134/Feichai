package com.example.feichai.myapplication;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;


//搜索记录帮助类
public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {
    private static String name = "temp.db";
    private static Integer version = 1;

    public RecordSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //搜索的数据库
        String CREATE_RECORDS="create table records(id integer primary key autoincrement,name varchar(200))";
        //订单的数据库
        String CREATE_ORDER="create table orders(userId integer primary key autoincrement,id integer,name varchar(200),price Integer,weight integer,amount integer,time datatime,after_sales boolean,sale_status integer,foreign key(userId) references user(id) on delete cascade on update cascade)";
        //地图信息
        String CREATE_MAP="create table maps(userId integer primary key autoincrement ,trading boolean,recycle_X_coordinates integer,recycle_Y_coordinates integer,user_X_coordinates integer,user_Y_coordinates integer,agree_time datatime,pause_order boolean,foreign key(userId) references user(id) on delete cascade on update cascade)";
        //交易信息
        String CREATE_TRADE="create table trades(userId integer primary key autoincrement,id integer,trade_price integer,trade_weight integer,trade_sumprice integer,finish_time datatime,foreign key(userId) references user(id) on delete cascade on update cascade)";
        //价目表
        String CREATE_PRICE="create table prices(material_id integer primary key autoincrement,release_data datatime,material_name String,material_price integer,material_science String)";
        //聊天表
        String CREATE_CONVERSATION="create table conversation (userId integer primary key autoincrement,id integer ,AdministratorId integer,,conversation String,conversationData datatime,foreign key(userId) references user(id) on delete cascade on update cascade)";

        db.execSQL(CREATE_RECORDS);
        db.execSQL(CREATE_ORDER);
        db.execSQL(CREATE_MAP);
        db.execSQL(CREATE_TRADE);
        db.execSQL(CREATE_PRICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

