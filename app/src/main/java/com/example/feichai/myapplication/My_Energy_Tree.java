package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.SQLiteDataBase.Energy_Tree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class My_Energy_Tree extends Activity {

    private Energy_Tree_WaterView mWaterView;
    String user_name;

    private List<Energy_Tree_Water> mWaters = new ArrayList<>();
    List<Integer> list = new ArrayList<>();
    Energy_Tree energy_tree = new Energy_Tree();

    TextView energy_count,energy_need,energy_tree_percent;
    static int count = 0;
    static int need = 0;
    int find_count,find_need,find_level,find_percent;
    int i=0;

    private ProgressBar progressBar;
    public Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_my_energy_tree);


        ActivityManager.getInstance().addActivity(this);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        find_count = intent.getIntExtra("count",0);
        find_level = intent.getIntExtra("level",0);
        find_need = intent.getIntExtra("need",0);
        find_percent = intent.getIntExtra("percent",0);

        System.out.println("传值：");
        System.out.println(find_count);
        System.out.println(find_level);
        System.out.println(find_need);
        System.out.println(find_percent);

        mWaterView = findViewById(R.id.wv_water);
        mWaterView.setWaters(mWaters);

        initView();

        energy_count = (TextView)findViewById(R.id.my_energy_tree_energyCount);
        energy_need = (TextView)findViewById(R.id.my_energy_tree_energyNeed);
        energy_tree_percent = (TextView)findViewById(R.id.energy_tree_percent);

        new Thread(runnable1).start();

        set_Text(find_count,find_need,find_percent);

        }

        public void set_Text(int count,int need,int percent)
        {
            energy_count.setText(String.valueOf(count));
            energy_need.setText(String.valueOf(need));
            energy_tree_percent.setText(String.valueOf(percent));
        }

    public Runnable runnable  = new Runnable(){
        @Override
        public void run() {
            //i控制百分度
            i++;
            if(i<find_percent) {
//                if (i == 5) {
//                    //删除指定的Runnable对象，使线程对象停止运行
//                    handler.removeCallbacks(this);
//                }
                progressBar.setProgress(i);
                handler.postDelayed(this, 30);
            }
        }
    };

    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            try{
                System.out.println("进入水滴查询");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);
                System.out.println("连接成功");

                Statement stmt = con.createStatement();
                String sql = "select * from energy_water where (user_id = '"+user_name+" 'and is_collect = "+0+")";
                ResultSet rs = stmt.executeQuery(sql);

                int i;
                while (rs.next() == true)
                {
                    i = rs.getInt("energy_water_count");
                    list.add(i);
                    System.out.println(i);
                    System.out.println("长度："+list.size());
                    mWaters.add(new Energy_Tree_Water(i, "能量"));

                    Log.i("TAG","查询水滴成功11");
                    //Toast.makeText(MainActivity_Out.this, "登录成功", Toast.LENGTH_LONG).show();

                }
                onRest(mWaterView);

                System.out.println("wai长度："+list.size());

                rs.close();
                stmt.close();
            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","获取水滴失败");
                Toast.makeText(My_Energy_Tree.this, "获取水滴失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };

        public void onRest(View view) {
        mWaterView.setWaters(mWaters);
        }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        handler.postDelayed(runnable, 100);

    }

    public void prev(View view){
        Intent intent2 = new Intent(My_Energy_Tree.this,Personal_Center.class);
        intent2.putExtra("name",user_name);
        My_Energy_Tree.this.startActivity(intent2);
    }


}
