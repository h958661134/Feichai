package com.example.feichai.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class SettingItemAdapter extends ArrayAdapter {

    private final int resourceId;           // 当前视图id
    private final Context currentContext;   // 当前上下文
    private LayoutInflater layoutInflater;

    public SettingItemAdapter(Context context, int textViewResourceId, List<SettingItem> items) {
        super(context, textViewResourceId, items);
        resourceId = textViewResourceId;
        currentContext = context;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public ImageView getUserHead(int userHead) {
        ImageView imageView = new ImageView(currentContext);    // 实例化一个ImageView对象
        imageView.setImageResource(userHead);                   // 设置图片为用户头像
        return imageView;                                       // 返回该对象
    }
    public Switch getSwitch() {
        Switch thisSwitch = new Switch(currentContext);         // 实例化一个Switch对象
        return thisSwitch;                                      // 返回该对象
    }
    public void detail2UserHead(ViewGroup view, int userHead) {             // 传入遍历的根ViewGroup
        View delete;                                                        // 声明delete为要删除的View
        int deleteId = currentContext.getResources().getIdentifier("itemDetailTV", "id", "com.example.acer.myapplication"); // 要删除的控件id
        for (int i = view.getChildCount(); i > 0; i--) {                    // 遍历view的子view个数次（必须反向遍历）
            delete = view.getChildAt(i - 1);                          // 将当前view赋给delete
            if (delete instanceof TextView) {                               // 如果该view是TextView
                if (delete.getId() == deleteId) {                           // 若当前控件id与要删除控件id相同
                    view.removeView(delete);                                // 删除该view
                    view.addView(getUserHead(userHead), 0);           // 获取用户头像并插入
                }
            } else if (delete instanceof ViewGroup) {                       // 如果当前view为ViewGroup
                detail2UserHead((ViewGroup) delete, userHead);              // 递归
            }
        }
    }
    public void next2Switch(ViewGroup view) {                               // 传入遍历的根ViewGroup
        View delete;                                                        // 声明delete为要删除的View
        int deleteId = currentContext.getResources().getIdentifier("itemNextIV", "id", "com.example.acer.myapplication"); // 要删除的控件id
        for (int i = view.getChildCount(); i > 0; i--) {                    // 遍历view的子view个数次（必须反向遍历）
            delete = view.getChildAt(i - 1);                          // 将当前view赋给delete
            if (delete instanceof ImageView) {                              // 如果该view是ImageView
                if (delete.getId() == deleteId) {                           // 若当前控件id与要删除控件id相同
                    view.removeView(delete);                                // 删除该view
                    view.addView(getSwitch());                              // 获取一个Switch对象并插入
                }
            } else if (delete instanceof ViewGroup) {                       // 如果当前view为ViewGroup
                next2Switch((ViewGroup) delete);                            // 递归
            }
        }
    }

    @NonNull
    @Override
    //设置空的间隔
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SettingItem item = (SettingItem) getItem(position);
        View view = layoutInflater.inflate(R.layout.setting_item,null);

        TextView itemTV = (TextView) view.findViewById(R.id.itemTV);
        TextView itemDetailTV = (TextView) view.findViewById(R.id.itemDetailTV);
        LinearLayout itemLL = (LinearLayout) view.findViewById(R.id.itemLL);
        LinearLayout itemRightLL = (LinearLayout) view.findViewById(R.id.itemRightLL);

        if (item.isDivider()) {                                                 // 若该item的isDivider属性值为true
            itemLL.removeView(itemRightLL);                                     // 删除item右侧部分
            itemLL.setBackgroundColor(Color.parseColor("#EEEEEE"));  // 设置背景色
            itemLL.setMinimumHeight(0);                                         // 设置高度
        }
        if (item.showSwitch()) {                    // 若该item的showSwitch值为true
            itemRightLL.removeView(itemDetailTV);   // 删除detail部分
            next2Switch(itemLL);                    // 添加Switch
        }
        if (item.getUserHead() != 0) {                      // 若该item有用户头像id
            detail2UserHead(itemLL, item.getUserHead());    // 添加ImageView
        }
        itemTV.setText(item.getText());                 // 设置文字
        itemDetailTV.setText(item.getDetailText());     // 设置detail文字

        return view;
    }




}
