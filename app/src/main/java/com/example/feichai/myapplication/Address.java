package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feichai.myapplication.SQLiteDataBase.CommonUtil;
import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.SQLiteDataBase.UserService;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Address extends Activity {

    private final String TAG = MainActivity.class.getName();
    private ListView lv;
    public Address_ListView address_listView;
    public static Address_Adapter adapter;
    public Address_Items address_items;
    public String address;
    static String choice_address;
    static String change_address;
    private static int count = 0;
    TextView use,del,edit;
    String user_name;
    static boolean change = false;
    static boolean choice = false;
    static Context context;
    int map_len;
    static int change_position;
    String manage_id,nearname;
    /**
     * ListView的item
     */
    public View itemView;
    private List<Address_Items> list = new ArrayList<>();
    static Map<String,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);

        getContext();
        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        nearname = intent.getStringExtra("nearname");
        manage_id =intent.getStringExtra("manage_name");

        System.out.println("      "+user_name+"   "+nearname+"    "+manage_id);

        ActivityManager.getInstance().addActivity(this);

        initView();
        // 实例化 adapter
        adapter = new Address_Adapter(Address.this,list,map);
        // listview绑定adapter适配器
        lv.setAdapter(adapter);

        //map.put("地址1","河南大学金明校区");
        map_len = map.size()+1;
        System.out.println("长度："+map.size());

        for (int i=0;i<map.size();i++) {
            String list_address = CommonUtil.getSettingNote(Address.this,"info","地址"+(i+1));
            list.add(new Address_Items(list_address));

        }

    }

    public static Context getContext() {
        return context;
    }

    /** 添加item */
    public void add(final View view) {

          AlertDialog.Builder builder = new AlertDialog.Builder(this);
          builder.setTitle("交易地点");

          final View v =  LayoutInflater.from(getApplication()).inflate(R.layout.main_info_dialog, null);
          EditText username = (EditText)v.findViewById(R.id.Info_near_name);
          username.setText(address);

          builder.setView(v);

          builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText editText_username = (EditText)v.findViewById(R.id.Info_near_name);
                    address = editText_username.getText().toString();

                    Toast.makeText(Address.this, "user_nearname:"+ address,Toast.LENGTH_SHORT).show();

                    list.add(new Address_Items(address));
                    map.put("地址"+map_len,address);
                    System.out.println("添加后长度："+map.size());
                    map_len = map_len+1;
                    CommonUtil.saveSettingNote(Address.this, "info",map);
                    adapter.notifyDataSetChanged();
                    count++;

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.show();

        Log.i(TAG, "count---" + count);	}

        public static void set_Address(String address){
          adapter.notifyDataSetChanged();
          choice_address = address;
          System.out.println(address);
          choice = true;
        }

        public static void change_Address(String address,int position){
            change_address = address;
            change_position = position;
            change = true;

        }


        public void go_Activity(){
            Intent intent = new Intent(Address.this,Trade_Activity.class);
            intent.putExtra("address",choice_address);
            intent.putExtra("name",user_name);
            intent.putExtra("manage_name",manage_id);
            intent.putExtra("nearname",nearname);
            intent.putExtra("from",1);
            System.out.println("      "+user_name+"   "+nearname+"    "+manage_id);
            Address.this.startActivity(intent);
        }


    private void initView() {
        lv = (ListView) findViewById(R.id.address);
        use = (TextView)findViewById(R.id.use);
        edit = (TextView) findViewById(R.id.edit);
        del = (TextView) findViewById(R.id.delete);
        //初始化控件
              }


    public void prev(View view){
        if(change == false){
        Intent in=getIntent();
        //设置返回结果成功
        setResult(RESULT_OK, in);
        //关闭当前activity
        finish();}
        else if (choice == true){
            if (change == true){
                map.put("地址"+(change_position+1),change_address);
                CommonUtil.saveSettingNote(Address.this, "info",map);
            }
            go_Activity();
        }
    }

}
