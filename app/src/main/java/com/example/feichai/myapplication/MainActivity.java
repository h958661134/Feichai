package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.bannerLib.IndicatorLocation;
import com.example.feichai.myapplication.bannerLib.LoopLayout;
import com.example.feichai.myapplication.bannerLib.LoopStyle;
import com.example.feichai.myapplication.bannerLib.OnDefaultImageViewLoader;
import com.example.feichai.myapplication.bean.BannerInfo;
import com.example.feichai.myapplication.listener.OnBannerItemClickListener;
import com.example.feichai.myapplication.view.BannerBgContainer;
import com.example.feichai.myapplication.zxing.activity.CaptureActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnBannerItemClickListener {

    private EditText mainet_search;
    private ImageView mainiv_conversation;
    private  ImageView main_camera;
    private ViewPager viewpager;
    private ImageView main_classify,dd5;
    private List<View> views=new ArrayList<>();
    BannerBgContainer bannerBgContainer;
    LoopLayout loopLayout;
    String user_name,manage_name,user_nearname;
    int find_count ;
    LoadingDialog dialog;
    Context cxt;

    Handler handler1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityManager.getInstance().addActivity(this);

        handler1=new Handler();
        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        user_nearname = intent.getStringExtra("nearname");
        manage_name =intent.getStringExtra("manage_name");


        cxt = this;
        dialog = new LoadingDialog(cxt);


        new Thread(runnable).start();

        System.out.println("用户"+user_name+"进入MainActivity");

        setFullScreen();
        loopLayout = (LoopLayout) findViewById(R.id.loop_layout);
        bannerBgContainer = (BannerBgContainer) findViewById(R.id.banner_bg_container);
        loopLayout.setLoop_ms(3000);//轮播的速度(毫秒)
        loopLayout.setLoop_duration(400);//滑动的速率(毫秒)
        loopLayout.setScaleAnimation(true);// 设置是否需要动画
        loopLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        loopLayout.setIndicatorLocation(IndicatorLocation.Center);//指示器位置-中Center
        loopLayout.initializeData(this);
        // 准备数据
        ArrayList<BannerInfo> bannerInfos = new ArrayList<>();
        List<Object> bgList = new ArrayList<>();
        bannerInfos.add(new BannerInfo(R.mipmap.banner_1, "first"));
        bannerInfos.add(new BannerInfo(R.mipmap.banner_2, "second"));
        bgList.add(R.mipmap.banner_bg1);
        bgList.add(R.mipmap.banner_bg2);
        // 设置监听
        loopLayout.setOnLoadImageViewListener(new OnDefaultImageViewLoader() {
            @Override
            public void onLoadImageView(ImageView view, Object object) {
                Glide.with(view.getContext())
                        .load(object)
                        .into(view);
            }
        });

        loopLayout.setOnBannerItemClickListener(this);
        if (bannerInfos.size() == 0) {
            return;
        }
        loopLayout.setLoopData(bannerInfos);
        bannerBgContainer.setBannerBackBg(this, bgList);
        loopLayout.setBannerBgContainer(bannerBgContainer);
        loopLayout.startLoop();


        //调整搜索按钮大小
        mainet_search = (EditText) findViewById(R.id.mainet_search);
        Drawable drawable = getResources().getDrawable(R.drawable.topsearch);
        drawable.setBounds(0, 0, 80, 80);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
        mainet_search.setCompoundDrawables(drawable, null, null, null);// 只放左边

        //实现搜索
        mainiv_conversation = (ImageView) findViewById(R.id.mainiv_conversation);
        mainiv_conversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search = mainet_search.getText().toString().trim();
                        if (search.length() != 0) {
                            Intent intent = new Intent();

                            intent.putExtra("search", search);
                            intent.putExtra("manage_id", manage_name);
                            intent.putExtra("name", user_name);
                            intent.putExtra("near_name", user_nearname);
                            System.out.println("nicheng" + user_nearname);
                            intent.setClass(MainActivity.this, ChatActivity.class);
                            startActivity(intent);
                        }

            }
        });

        // 搜索框的键盘搜索键点击回调
        mainet_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    Toast.makeText(MainActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("name",user_name);
                    intent.putExtra("user_nearname",user_nearname);
                    intent.setClass(MainActivity.this, ChatActivity.class);
                }
                return false;
            }
        });


        main_camera = (ImageView) findViewById(R.id.main_camera);
        main_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
            }
        });

        //实现水平滑动
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        //添加滑动的3个布局
        views.add(getLayoutInflater().inflate(R.layout.layout_viewpager, null));
        views.add(getLayoutInflater().inflate(R.layout.layout_viewpager_left_one, null));
        views.add(getLayoutInflater().inflate(R.layout.layout_viewpager_left_two, null));
        viewpager.setAdapter(new Myadapter());

    }



    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            try{
                System.out.println("进入Tree查询");

                Connection con = DatabaseHelper.getCon("root","123456");
                System.out.println(con);

                String sql = "select energy_count from energy_tree where user_id = "+user_name;
                System.out.println("Tree连接成功");
                PreparedStatement pstmt;
                pstmt = (PreparedStatement)con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                Log.i("TAG","将要进入Tree");

                if (rs.next() == true)
                {

                    Log.i("TAG","进入Tree成功");

                    find_count = rs.getInt(1);
                    System.out.println("count: "+find_count);

                    Looper.prepare();
                    Log.i("TAG","查询Tree成功");
                    //Toast.makeText(MainActivity_Out.this, "登录成功", Toast.LENGTH_LONG).show();
                    Looper.loop();

                }
                rs.close();
                pstmt.close();

            }catch (SQLException e)
            {
                Looper.prepare();
                Log.i("TAG","获取Tree失败");
                Toast.makeText(MainActivity.this, "获取Tree失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }
    };


//    /*用于查询用户对应的管理员昵称,用户昵称*/
//    Runnable selectrunnable1 = new Runnable() {
//        @Override
//        public void run() {
//            try{
//                Connection con = DatabaseHelper.getCon("root","123456");
//                //从数据库中查询
//                String selectsql="select * from user where user_name="+user_name;
//                PreparedStatement stmt=con.prepareStatement(selectsql);
//                ResultSet rs=stmt.executeQuery();
//                System.out.println("-------------");
//                if(rs.next())
//                {
//                    manage_name = rs.getString("manage_id");
//                    user_nearname = rs.getString("user_nearname");
//                    System.out.println(manage_name);
//                    System.out.println(user_nearname);
//                }
//                dialog.dismiss();
//
//                handler1.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        String search = mainet_search.getText().toString().trim();
//                        if (search.length() != 0) {
//                            Intent intent = new Intent();
//
//                            intent.putExtra("search", search);
//                            intent.putExtra("manage_id", manage_name);
//                            intent.putExtra("name", user_name);
//                            intent.putExtra("near_name", user_nearname);
//                            System.out.println("nicheng" + user_nearname);
//                            intent.setClass(MainActivity.this, ChatActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//                });
//                stmt.close();
//                con.close();
//                Looper.prepare();
//                //Toast.makeText(MainActivity.this, "查询管理员信息成功", Toast.LENGTH_LONG).show();
//                Looper.loop();
//
//            }catch (SQLException e)
//            {
//                e.printStackTrace();
//                Looper.prepare();
//                Log.i("TAG","查询管理员信息失败");
//                Toast.makeText(MainActivity.this, "查询管理员信息失败", Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        }
//    };

    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){
                result.append(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    //四个按钮都跳转到一个聊天界面
    public void onclick(View view) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,IntegralActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBannerClick(int index, ArrayList<BannerInfo> banner) {

    }


    //水平滑动适配类
    class Myadapter extends PagerAdapter{

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v=views.get(position);
            //
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v=views.get(position);
            container.removeView(v);
        }

    }

    void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //点击事件
    public void skip_to_activity(View view){

        switch (view.getId()){

            case R.id.personal_center:
                Intent intent1 = new Intent(MainActivity.this,Personal_Center.class);
                intent1.putExtra("name",user_name);
                intent1.putExtra("manage_name",manage_name);
                intent1.putExtra("nearname",user_nearname);
                MainActivity.this.startActivity(intent1);
                break;
            case R.id.classify:
                Intent intent2 = new Intent(MainActivity.this,ClassifyActivity.class);
                intent2.putExtra("name",user_name);
                intent2.putExtra("manage_name",manage_name);
                intent2.putExtra("nearname",user_nearname);
                MainActivity.this.startActivity(intent2);
                break;
            case R.id.trade:
                Intent intent3 = new Intent(MainActivity.this,Trade_Activity.class);
                intent3.putExtra("name",user_name);
                intent3.putExtra("manage_name",manage_name);
                intent3.putExtra("nearname",user_nearname);
                intent3.putExtra("from",0);
                MainActivity.this.startActivity(intent3);
                break;
            case R.id.map:
                Intent intent4 = new Intent(MainActivity.this,MapActivity.class);
                intent4.putExtra("name",user_name);
                intent4.putExtra("manage_name",manage_name);
                intent4.putExtra("nearname",user_nearname);
                MainActivity.this.startActivity(intent4);
                break;

            default:break;


        }

    }



}
