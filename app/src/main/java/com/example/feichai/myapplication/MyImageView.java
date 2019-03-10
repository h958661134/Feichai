package com.example.feichai.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MyImageView extends android.support.v7.widget.AppCompatImageView {
    private int mscreenHeight;
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mscreenHeight=getHeight(getContext());
    }
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
    {
        heightMeasureSpec=MeasureSpec.makeMeasureSpec(mscreenHeight,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    public static int getHeight(Context context)
    {
        DisplayMetrics dm=new DisplayMetrics();
        WindowManager mwm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mwm.getDefaultDisplay().getMetrics(dm);
        int screenheight=dm.heightPixels;
        return screenheight;
    }
}
