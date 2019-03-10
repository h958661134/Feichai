package com.example.feichai.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    //ListView视图内容由IMsgviewtype决定
    public static interface IMsgViewType
    {
        //对方发来消息
        int IMVT_COM_MSG = 0;
        //自己发出消息
        int IMVT_TO_MSG = 1;
    }

    private static final String TAG = ChatAdapter.class.getSimpleName();
    private List<ChatMsgEntity> data;
    private Context context;
    private LayoutInflater mInflater;

    public ChatAdapter(Context context, List<ChatMsgEntity> data) {
        this.context = context;
        this.data = data;
        //LayoutInflater类似findViewById，只是LayoutInflater是找res/layout/下的.xml文件
        mInflater = LayoutInflater.from(context);
    }

    //获取ListView
    public int getCount() {
        return data.size();
    }

    //获取项
    public Object getItem(int position) {
        return data.get(position);
    }

    //获取项的ID
    public long getItemId(int position) {
        return position;
    }

    //获取项的类型
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        ChatMsgEntity entity = data.get(position);

        if (entity.getMsgType())
        {
            return IMsgViewType.IMVT_COM_MSG;
        }else{
            return IMsgViewType.IMVT_TO_MSG;
        }

    }

    //获取项的类型数
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    //获取View
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMsgEntity entity = data.get(position);
        boolean isComMsg = entity.getMsgType();

        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            if (isComMsg)
            {
                //如果是对方发来的消息，则显示左气泡
                convertView = mInflater.inflate(R.layout.conversion_left, null);
                viewHolder = new ViewHolder();
                viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_servicesendtime);
                viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_servicename);
                viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_servicechat);

            }else{
                //如果是自己发来的消息，则显示右气泡
                convertView = mInflater.inflate(R.layout.conversion_right, null);
                viewHolder = new ViewHolder();
                viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
                viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
                viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_userchat);

            }
            viewHolder.isComMsg = isComMsg;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSendTime.setText(entity.getDate());
        viewHolder.tvUserName.setText(entity.getName());
        viewHolder.tvContent.setText(entity.getText());

        return convertView;
    }

    //通过ViewHolder显示项的内容
    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }
}
