package com.example.feichai.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.Bill;
import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.SQLiteDataBase.Energy_Tree;
import com.example.feichai.myapplication.SQLiteDataBase.Energy_Water;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Trade_Activity extends Activity {

    private ImageView dd,main_classify,to_address;
    private ImageView iv_choose,iv_turnnext;
    private TextView tv_choose,tv_phone,tv_address;
    private EditText tv_goods_name,tv_goods_logo,tv_goods_weight,tv_bill_money;
    private Button buttontijiao;
    String user_name,bill_address,goods_name,goods_logo,bill_id,manage_id,choice_address,pick_time,nearname;
    float goods_weight=0,bill_money=0;
    public Timestamp formatter;
    Bill bill = new Bill();
    Energy_Water energy_water = new Energy_Water();
    Energy_Tree energy_tree = new Energy_Tree();
    int which_com,old_energy_count,old_tree_level,old_energy_need,old_energy_percent;
    int change_energy_count,new_energy_count;
    LoadingDialog dialog;
    Context cxt;
    private String data;
    private String time;
    StringBuffer sbBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_layout);

        cxt = this;
        dialog = new LoadingDialog(cxt);

        ActivityManager.getInstance().addActivity(this);

        Intent intent = this.getIntent();
        user_name = intent.getStringExtra("name");
        nearname = intent.getStringExtra("nearname");
        manage_id =intent.getStringExtra("manage_name");
        System.out.println("      "+user_name+"   "+nearname+"    "+manage_id);
        which_com = intent.getIntExtra("from",0);

        if (which_com==1){
            choice_address = intent.getStringExtra("address");
        }

        System.out.println("用户"+user_name+"进入Trade");

        System.out.println("来源"+which_com);

        tv_address = (TextView)findViewById(R.id.trade_address);
        if (which_com == 1){
            tv_address.setText(choice_address);
        }
        tv_goods_name = (EditText)findViewById(R.id.trade_goods_name);
        tv_goods_logo = (EditText)findViewById(R.id.trade_goods_logo);
        tv_goods_weight = (EditText)findViewById(R.id.trade_goods_weight);
        tv_bill_money = (EditText)findViewById(R.id.trade_bill_money);

        //交易时间
        tv_choose=(TextView)findViewById(R.id.tv_choose);

        iv_choose=(ImageView)findViewById(R.id.iv_choose);
        iv_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPick(tv_choose);
            }
        });

        tv_phone=(TextView)findViewById(R.id.tv_phone);

        iv_turnnext=(ImageView)findViewById(R.id.iv_turnnext);
        iv_turnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                //intent.setData(Uri.parse(tv_phone.getText().toString()));
                intent.setData(Uri.parse("tel:4008666688"));
                startActivity(intent);
            }
        });

        to_address = (ImageView)findViewById(R.id.set_address);
        to_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trade_Activity.this,Address.class);
                intent.putExtra("name",user_name);
                intent.putExtra("manage_name",manage_id);
                intent.putExtra("nearname",nearname);
                Trade_Activity.this.startActivity(intent);
            }
        });

        //跳转成功界面
        buttontijiao=(Button)findViewById(R.id.button_tijiao);
        buttontijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    pick_time = tv_choose.getText().toString();
                    goods_name = tv_goods_name.getText().toString();
                    goods_logo = tv_goods_logo.getText().toString();
                    goods_weight = Integer.parseInt(tv_goods_weight.getText().toString());
                    bill_money = Integer.parseInt(tv_bill_money.getText().toString());

                    if((pick_time!=null)&(goods_name!=null)&(goods_logo!=null)&(goods_weight!=0)&(bill_money!=0)&(choice_address!=null)) {
                        bill_id = getMyUUID();
                        bill.setBill_exchange_time(pick_time);
                        bill.setManage_id(manage_id);
                        bill.setTrade_address(choice_address);
                        bill.setGoods_name(goods_name);
                        bill.setGoods_logo(goods_logo);
                        bill.setGoods_weight(goods_weight);
                        bill.setBill_money(bill_money);
                        bill.setBill_id(bill_id);
                        bill.setUser_id(user_name);
                        bill.setManage_id(manage_id);
                        bill.setBill_codition(false);
                        bill.setCustomer_service(4);

                        java.util.Date d = new java.util.Date();
                        formatter = new Timestamp(d.getTime());
                        bill.setBill_start_time(formatter);

                        //Energy——Water

                        energy_water.setUser_id(user_name);
                        energy_water.setEnergy_id(bill_id);
                        energy_water.setWater_count(Integer.valueOf(tv_bill_money.getText().toString()));
                        energy_water.setProduce_time(formatter);
                        energy_water.setIs_collect(0);

                        new Thread(runnable3).start();

                        new Thread(runnable1).start();

                        new Thread(runnable2).start();

                        new Thread(runnable4).start();

//                        new Thread(runnable5).start();
                    }
                    else {
                        Looper.prepare();
                        Toast.makeText(Trade_Activity.this, "有未填写信息！", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                }

        });
    }

    //获取UUID
    public String getMyUUID(){

        UUID uuid = UUID.randomUUID();

        String uniqueId = uuid.toString();

        Log.d("debug","----->UUID"+uuid);

        return uniqueId;

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

    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {

            System.out.println("进入记录");

            Connection con = DatabaseHelper.getCon("root","123456");
            System.out.println(con);
            System.out.println("连接");

            String sql="insert into energy_water(energy_id,user_id,energy_water_count,produce_time,is_collect) values(?,?,?,?,?)";
            PreparedStatement pstmt;
            try{
                System.out.println("开始记录");
                pstmt = (PreparedStatement)con.prepareStatement(sql);
                pstmt.setString(1,energy_water.getEnergy_id());
                pstmt.setString(2,energy_water.getUser_id());
                pstmt.setInt(3,energy_water.getWater_count());
                pstmt.setTimestamp(4,energy_water.getProduce_time());
                pstmt.setInt(5,energy_water.getIs_collect());

                System.out.println("水滴记录完毕");
                int i=pstmt.executeUpdate();
                System.out.println(i);
                System.out.println("水滴更新成功");
                pstmt.close();
                con.close();
                System.out.println("水滴结束");

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","水滴记录失败");
                Toast.makeText(Trade_Activity.this, "记录失败", Toast.LENGTH_LONG).show();
                Looper.loop();
                e.printStackTrace();
            }
        }
    };



    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {

            System.out.println("进入记录");

            Connection con = DatabaseHelper.getCon("root","123456");
            System.out.println(con);
            System.out.println("连接");

            String sql="insert into bill(bill_id,user_id,goods,bill_start_time,bill_money,goods_weight,manage_id,bill_condition,customer_service,goods_logo,bill_trade_address,bill_exchange_time) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql2="insert into commit(com_id,user_id,manage_id,com_date,com_time,com_content,user_nearname,sign) value(?,?,?,?,?,?,?,?)" ;
            PreparedStatement pstmt2;
            PreparedStatement pstmt;
            try{
                getDate();
                System.out.println("开始记录");
                pstmt = (PreparedStatement)con.prepareStatement(sql);
                pstmt.setString(1,bill.getBill_id());
                pstmt.setString(2,bill.getUser_id());
                pstmt.setString(3,bill.getGoods_name());
                pstmt.setTimestamp(4,bill.getBill_start_time());
                pstmt.setFloat(5,bill.getBill_money());
                pstmt.setFloat(6,bill.getGoods_weight());
                pstmt.setString(7,manage_id);
                pstmt.setBoolean(8,bill.isBill_codition());
                pstmt.setInt(9,bill.getCustomer_service());
                pstmt.setString(10,bill.getGoods_logo());
                pstmt.setString(11,bill.getTrade_address());
                pstmt.setString(12,bill.getBill_exchange_time());

                System.out.println("记录输入");
                String context="用户："+user_name+"在"+data+" "+time+"约定在"+choice_address+",进行交易。交易物品为:"+bill.getGoods_name()+"  品牌为："+bill.getGoods_logo()+" 量为:"+bill.getGoods_weight()+",预估价格为："+bill.getBill_money();
                System.out.println(context);
                pstmt2 = (PreparedStatement)con.prepareStatement(sql2);
                pstmt2.setString(1,getMyUUID());
                pstmt2.setString(2,user_name);
                pstmt2.setString(3,manage_id);
                pstmt2.setString(4,data);
                pstmt2.setString(5,time);
                pstmt2.setString(6,context);
                pstmt2.setString(7,nearname);
                pstmt2.setInt(8,1);

                System.out.println(user_name+manage_id+data+time+context+nearname);

                System.out.println("记录完毕");
                int i=pstmt.executeUpdate();
                int i2=pstmt2.executeUpdate();
                System.out.println("状态为"+i2+i);
                System.out.println("开始更新");
                pstmt.close();
                con.close();
                pstmt2.close();
                System.out.println("更新结束");
                Intent intent = new Intent(Trade_Activity.this,Tradesuccess.class);
                intent.putExtra("name",user_name);
                intent.putExtra("data",formatter);
                Trade_Activity.this.startActivity(intent);

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","插入数据记录失败");
                Toast.makeText(Trade_Activity.this, "记录失败", Toast.LENGTH_LONG).show();

                Looper.loop();
                e.printStackTrace();
            }

        }
    };


//    Runnable runnable5 =new Runnable() {
//        @Override
//        public void run() {
//            System.out.println("进入信息记录");
//
//            Connection con = DatabaseHelper.getCon("root","123456");
//            System.out.println(con);
//            System.out.println("连接");
//
//            System.out.println("连接");
//
//
//            String sql="insert into commit(com_id,user_id,manager_id,com_date,com_time,com_content,user_nearname) value(?,?,?,?,?,?,?)" ;
//            PreparedStatement pstmt;
//
//            try{
//                System.out.println("信息开始记录");
//                String context="用户："+bill.getUser_id()+"在"+data+" "+time+"约定在"+choice_address+",进行交易。交易物品为:"+bill.getGoods_name()+"  品牌为："+bill.getGoods_logo()+" 量为:"+bill.getGoods_weight()+",预估价格为："+bill.getBill_money();
//                pstmt = (PreparedStatement)con.prepareStatement(sql);
//                pstmt.setString(1,getMyUUID());
//                pstmt.setString(2,bill.getUser_id());
//                pstmt.setString(3,bill.getManage_id());
//                pstmt.setString(4,data);
//                pstmt.setString(5,time);
//                pstmt.setString(6,context);
//                pstmt.setString(7,nearname);
//
//                System.out.println("信息记录完毕");
//                int i=pstmt.executeUpdate();
//                System.out.println(i);
//                System.out.println("信息更新");
//                pstmt.close();
//                con.close();
//                System.out.println("信息结束");
//
//            }catch (SQLException e)
//            {
//                Looper.prepare();
//                Log.i("TAG","信息记录失败");
//                Toast.makeText(Trade_Activity.this, "记录失败", Toast.LENGTH_LONG).show();
//
//                Looper.loop();
//                e.printStackTrace();
//            }
//        }
//    };


    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            try{
                System.out.println("进入Tree查询");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("连接成功");

                Statement stmt = con.createStatement();
                String sql = "select * from energy_tree where user_id = "+user_name;
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next())
                {
                    old_energy_count = rs.getInt("energy_count");
                    old_tree_level = rs.getInt("tree_level");
                    old_energy_need = rs.getInt("energy_need");
                    old_energy_percent = rs.getInt("energy_percent");

                    rs.close();
                    stmt.close();
                    Looper.prepare();

                    Log.i("TAG","查询Tree成功");

                    //Toast.makeText(MainActivity_Out.this, "登录成功", Toast.LENGTH_LONG).show();
                    Looper.loop();

                }
            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","获取Tree失败");
                Toast.makeText(Trade_Activity.this, "获取Tree失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };


    Runnable runnable4 = new Runnable() {
        @Override
        public void run() {

            System.out.println("进入Tree更新");
            Connection con = DatabaseHelper.getCon("root","123456");
            System.out.println(con);
            System.out.println("进入更新");

            //Energy_Tree赋值
            change_energy_count =  old_energy_count + Integer.valueOf(tv_bill_money.getText().toString());
            if(change_energy_count>99)
            {
                new_energy_count = change_energy_count - 100;
                old_energy_need = 100 - new_energy_count;
                old_tree_level +=1;
                old_energy_percent = new_energy_count;

            }
            else{
                new_energy_count = change_energy_count;
                old_energy_need = 100-new_energy_count;
                old_energy_percent = new_energy_count;
            }

            energy_tree.setEnergy_count(new_energy_count);
            energy_tree.setEnergy_need(old_energy_need);
            energy_tree.setTree_level(old_tree_level);
            energy_tree.setEnergy_percent(old_energy_percent);

            String sql = "update energy_tree set energy_count = "+energy_tree.getEnergy_count()+",tree_level = "+energy_tree.getTree_level()+",energy_need = "+energy_tree.getEnergy_need()+",energy_percent = "+energy_tree.getEnergy_percent()+" where user_name = "+user_name;
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
                Toast.makeText(Trade_Activity.this, "更新成功", Toast.LENGTH_LONG).show();
                Looper.loop();

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","能量树更新失败");
                //Toast.makeText(Trade_Activity.this, "更新失败", Toast.LENGTH_LONG).show();
                Looper.loop();

            }
        }
    };



    //将两个选择时间的dialog放在该函数中
    private void showDialogPick(final TextView timeText) {
        final StringBuffer time = new StringBuffer();
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //实例化TimePickerDialog对象

        final TimePickerDialog timePickerDialog = new TimePickerDialog(Trade_Activity.this, new TimePickerDialog.OnTimeSetListener() {
            //选择完时间后会调用该回调函数
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(minute<10){time.append(" "  + hourOfDay + ":0" + minute);}
                else{time.append(" "  + hourOfDay + ":" + minute);}
                //                //设置TextView显示最终选择的时间
                timeText.setText(time);
            }
        }, hour, minute, true);
        //实例化DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(Trade_Activity.this, new DatePickerDialog.OnDateSetListener() {
            //选择完日期后会调用该回调函数
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //因为monthOfYear会比实际月份少一月所以这边要加1
                time.append(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                //选择完日期后弹出选择时间对话框
                timePickerDialog.show();
            }
        }, year, month, day);
        //弹出选择日期对话框
        datePickerDialog.show();
    }

    public void skip_to_activity(View view){

        switch (view.getId()){

            case R.id.main:
                Intent intent1 = new Intent(Trade_Activity.this,MainActivity.class);
                intent1.putExtra("name",user_name);
                intent1.putExtra("manage_name",manage_id);
                intent1.putExtra("nearname",nearname);
                Trade_Activity.this.startActivity(intent1);
                break;
            case R.id.classify:
                Intent intent2 = new Intent(Trade_Activity.this,ClassifyActivity.class);
                intent2.putExtra("name",user_name);
                intent2.putExtra("manage_name",manage_id);
                intent2.putExtra("nearname",nearname);
                Trade_Activity.this.startActivity(intent2);
                break;
            case R.id.personal_center:
                Intent intent3 = new Intent(Trade_Activity.this,Personal_Center.class);
                intent3.putExtra("name",user_name);
                intent3.putExtra("manage_name",manage_id);
                intent3.putExtra("nearname",nearname);
                Trade_Activity.this.startActivity(intent3);
                break;
            case R.id.map:
                Intent intent4 = new Intent(Trade_Activity.this,MapActivity.class);
                intent4.putExtra("name",user_name);
                intent4.putExtra("manage_name",manage_id);
                intent4.putExtra("nearname",nearname);
                Trade_Activity.this.startActivity(intent4);
                break;

            default:break;
        }
    }

}


