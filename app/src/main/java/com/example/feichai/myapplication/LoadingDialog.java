package com.example.feichai.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

public class LoadingDialog extends Dialog {
    private TextView tv;

    AVLoadingIndicatorView avLoadingIndicatorView;


    public LoadingDialog(Context context) {
        super(context);
    } @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //去掉默认的title requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.progressbar);

        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avLoadingIndicatorView.show();
        Log.i("LHD", "LoadingDialog onCreate");
        tv = (TextView) findViewById(R.id.processbar_tv);
        tv.setText("正在加载.....");
//        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ProcessBar_LinearLayout);
//        linearLayout.getBackground().setAlpha(210);

    }
}
