package com.example.feichai.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class integralAdapter extends ArrayAdapter<Integral> {

    private int resourceId;
    public integralAdapter(@NonNull Context context, int resource, @NonNull List<Integral> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Integral integral_exchange=getItem(position);

        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView imageView=(ImageView) view.findViewById(R.id.iv_integral_image);
        TextView textView=(TextView) view.findViewById(R.id.tv_integral_textview);
        TextView textView1=(TextView)view.findViewById(R.id.tv_need);
        Button button=(Button)view.findViewById(R.id.button_exchange);

        //设置灰色button按钮
        if(position%5==2)
        {button.setBackgroundColor( -8355712);
        button.setText("已抢光");}
        else
        {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    //builder.setIcon(R.drawable.ic_launcher);//设置图标
                    builder.setTitle("choose");//设置对话框的标题
                    builder.setMessage("暂时不支持此项功能");//设置对话框的内容
                    /*builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getContext(), "兑换成功", Toast.LENGTH_SHORT).show();

                        }
                    });*/
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getContext(), "取消",Toast.LENGTH_SHORT).show();

                        }
                    });
                    AlertDialog b=builder.create();
                    b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                }
            });
        }
        imageView.setImageResource(integral_exchange.getImageId());
        textView.setText(integral_exchange.getName());
        textView1.setText(integral_exchange.getNeedIntegral());

        return view;
    }
}
