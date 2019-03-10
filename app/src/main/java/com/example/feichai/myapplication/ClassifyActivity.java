package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClassifyActivity extends Activity {

    private ImageView dd,dd5;
    private ViewPager left_viewpager;
    private List<View> views=new ArrayList<>();
    String user_name,manage_name,nearname;
    TextView find;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);

        ActivityManager.getInstance().addActivity(this);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        nearname = intent.getStringExtra("nearname");
        manage_name =intent.getStringExtra("manage_name");


        System.out.println("用户"+user_name+"进入Classify");

        //实现左侧栏滑动
        left_viewpager=(ViewPager)findViewById(R.id.left_viewpager);
        //添加滑动的1个布局
        views.add(getLayoutInflater().inflate(R.layout.left_layout_viewpager,null));
        views.add(getLayoutInflater().inflate(R.layout.left_layout_viewpager_two,null));
        views.add(getLayoutInflater().inflate(R.layout.left_layout_viewpager_three,null));
        left_viewpager.setAdapter(new Myadapter());

        left_viewpager.setOnTouchListener(new View.OnTouchListener() {
            int flag=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        flag=0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        flag=1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if(flag==0)
                        {
                            int item=left_viewpager.getCurrentItem();
                            if(item==0)
                            {
                                Intent intent=new Intent(ClassifyActivity.this,Trade_Activity.class);

                                startActivity(intent);
                            }
                            else if(item==1)
                            {
                                Intent intent=new Intent(ClassifyActivity.this,Trade_Activity.class);
                                startActivity(intent);
                            }
                            else if(item==2)
                            {
                                Intent intent=new Intent(ClassifyActivity.this,Trade_Activity.class);
                                startActivity(intent);
                            }
                        }
                        break;
                }

                return false;
            }
        });


    }


    public void skip_to_activity(View view){

        switch (view.getId()){

            case R.id.main:
                Intent intent1 = new Intent(ClassifyActivity.this,MainActivity.class);
                intent1.putExtra("name",user_name);
                intent1.putExtra("manage_name",manage_name);
                intent1.putExtra("nearname",nearname);
                ClassifyActivity.this.startActivity(intent1);
                break;
            case R.id.personal_center:
                Intent intent2 = new Intent(ClassifyActivity.this,Personal_Center.class);
                intent2.putExtra("name",user_name);
                intent2.putExtra("manage_name",manage_name);
                intent2.putExtra("nearname",nearname);
                ClassifyActivity.this.startActivity(intent2);
                break;
            case R.id.trade:
                Intent intent3 = new Intent(ClassifyActivity.this,Trade_Activity.class);
                intent3.putExtra("name",user_name);
                intent3.putExtra("manage_name",manage_name);
                intent3.putExtra("nearname",nearname);
                intent3.putExtra("from",0);
                ClassifyActivity.this.startActivity(intent3);
                break;
            case R.id.map:
                Intent intent4 = new Intent(ClassifyActivity.this,MapActivity.class);
                intent4.putExtra("name",user_name);
                intent4.putExtra("manage_name",manage_name);
                intent4.putExtra("nearname",nearname);
                ClassifyActivity.this.startActivity(intent4);
                break;

            default:break;


        }

    }

    class Myadapter extends PagerAdapter {

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
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v=views.get(position);
            container.removeView(v);
        }

    }

}
