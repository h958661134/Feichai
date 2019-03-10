package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class After_Sale extends Activity {

    private List<Integer> page;
    private List<String> title;
    private ViewPager after_sale_VP;

    private ImageView cursor;       //滚动条的动画。
    private int cursorWidth;        //动画的宽度。
    private int Offset;             //动画图片的偏移量。
    private int currIndex = 0;      //当前页码编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_after_sale);

        ActivityManager.getInstance().addActivity(this);
        init();
        initCursorPos();
        after_sale_VP = (ViewPager) findViewById(R.id.after_sale_VP);
        after_sale_VP.setAdapter(new After_Sale_Page_Adapter(this, page, title));
        after_sale_VP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int one = Offset * 2 + cursorWidth;// 页卡1 -> 页卡2 偏移量
            int two = one * 2;// 页卡1 -> 页卡3 偏移量

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            /*
            通过事件的监听，实现动画的切换
             */
            @Override
            public void onPageSelected(int position) {
                Animation animation = null;
                switch (position) {
                    case 0:
                        if (currIndex == 1) {
                            animation = new TranslateAnimation(one, 0, 0, 0);
                        } else if (currIndex == 2) {
                            animation = new TranslateAnimation(two, 0, 0, 0);
                        }
                        break;
                    case 1:
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(0, one, 0, 0);
                        } else if (currIndex == 2) {
                            animation = new TranslateAnimation(two, one, 0, 0);
                        }
                        break;
                    case 2:
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(0, two, 0, 0);
                        } else if (currIndex == 1) {
                            animation = new TranslateAnimation(one, two, 0, 0);
                        }
                        break;
                }
                currIndex = position;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(250);
                cursor.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }


    public void init() {
        page = new ArrayList<>();
        page.add(R.layout.after_sale_application);
        page.add(R.layout.after_sale_online);
        page.add(R.layout.after_sale_list);

        title = new ArrayList<>();
        title.add("申请售后");
        title.add("处理中");
        title.add("售后记录");
    }

    private void initCursorPos() {
        List<View> mViews = new ArrayList<>();
        mViews.add(View.inflate(this, page.get(0), null));
        mViews.add(View.inflate(this, page.get(1), null));
        mViews.add(View.inflate(this, page.get(2), null));
        // 初始化动画
        cursor = (ImageView) findViewById(R.id.after_sale_cursor);
        cursorWidth = BitmapFactory.decodeResource(getResources(), R.drawable.pagerview_title_cursor).getWidth();// 获取图片宽度
        //获得屏幕的宽度
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenW = metrics.widthPixels;
        // 计算偏移量
        Offset = (screenW / mViews.size() - cursorWidth) / 2;
        // 设置动画初始位置
        Matrix matrix = new Matrix();
        matrix.postTranslate(Offset, 0);
        cursor.setImageMatrix(matrix);
    }

    public void prev(View view){
        Intent in=getIntent();
        //设置返回结果成功
        setResult(RESULT_OK, in);
        //关闭当前activity
        finish();
    }


}
