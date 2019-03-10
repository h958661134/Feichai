package com.example.feichai.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.feichai.myapplication.SQLiteDataBase.DatabaseHelper;
import com.example.feichai.myapplication.SQLiteDataBase.UserService;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.feichai.myapplication.ImageUtil.CROP_CACHE_FILE_NAME;


public class Info_Change_Headpic extends Activity implements View.OnClickListener,ImageUtil.CropHandler {
    private Button selectPhoto,takePhoto;
    private ImageView imageView;
    int head_id;
    String user_name;
    UserService userService = new UserService(Info_Change_Headpic.this);
    String uri;
    Bitmap bitmap=null;
    Handler handler;
    byte[] imagedate;
    Uri photoUri;
    int image1;
    LoadingDialog dialog;
    Context cxt;
    static Boolean ifupdata=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_select_pic);
        selectPhoto = (Button) findViewById(R.id.buttonLocal);
        takePhoto = (Button) findViewById(R.id.buttonCamera);
        imageView = (ImageView)findViewById(R.id.imageView);
        selectPhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);

        ActivityManager.getInstance().addActivity(this);

        cxt = this;

        dialog = new LoadingDialog(cxt);

        Intent intent = getIntent();
        user_name = intent.getStringExtra( "user_name");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        handler=new Handler();



        new Thread(runnable).start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonLocal:
                //选择相册
                Intent galleryIntent = ImageUtil
                        .getCropHelperInstance()
                        .buildGalleryIntent();
                startActivityForResult(galleryIntent,
                        ImageUtil.REQUEST_GALLERY);
                break;
            case R.id.buttonCamera:
                //拍摄照片
                Intent cameraIntent = ImageUtil
                        .getCropHelperInstance()
                        .buildCameraIntent();
                startActivityForResult(cameraIntent,
                        ImageUtil.REQUEST_CAMERA);
                break;
        }
    }

    //点击应用按钮实现头像查询和加载
    public void apply(View view)
    {

        new Thread(runnable).start();

    }



    //获取相册返回的uri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageUtil.getCropHelperInstance().sethandleResultListerner( Info_Change_Headpic.this, requestCode, resultCode, data);
        Bitmap bm = null;
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        if(requestCode==ImageUtil.REQUEST_GALLERY)
        {
            try {

                Uri originalUri = data.getData(); // 获得图片的uri

                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bm, 100, 100));  //使用系统的一个工具类，参数列表为 Bitmap Width,Height  这里使用压缩后显示，否则在华为手机上ImageView 没有显示

                String[] proj = { MediaStore.Images.Media.DATA };
                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                String path = cursor.getString(column_index);
                uri=path;
                System.out.println("this is uri"+uri);

            } catch (IOException e) {
                Log.e("TAG-->Error", e.toString());
            }
            finally {
                return;
            }
        }


}



    @Override
    public void onPhotoCropped(Bitmap photo, int requestCode) {
        switch (requestCode){
            case ImageUtil.RE_GALLERY:
                imageView.setImageBitmap(photo);
                final ByteArrayOutputStream os = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, os);
                byte[] imagedate = os.toByteArray();
                int image =  byteArrayInt(imagedate);
                dialog.show();
                new Thread(run).start();

                break;
            case ImageUtil.RE_CAMERA:
                imageView.setImageBitmap(photo);
                final ByteArrayOutputStream os1 = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, os1);
                byte[] imagedate1 = os1.toByteArray();
                int image1 =  byteArrayInt(imagedate1);
                dialog.show();
                new Thread(run).start();
                break;
        }

    }

    public static int byteArrayInt(byte[] b) {
        int a = (((int) b[0]) << 24) + (((int) b[1]) << 16) + (((int) b[2]) << 8) + b[3];
        if (a < 0) {
            a = a + 256;
        }
        return a;
    }


    public void prev(View view){
        Intent in=getIntent();
        //设置返回结果成功
        setResult(RESULT_OK, in);
        //关闭当前activity
        finish();
    }

    @Override
    public void onCropCancel() {

    }

    @Override
    public void onCropFailed(String message) {

    }

    @Override
    public Activity getContext() {
        return Info_Change_Headpic.this;
    }

    /**
     * 以下两个线程均是选择相册图片需要的更新数据库，和数据库查询
     *
     * */
    Runnable run=new Runnable() {
        @Override
        public void run() {
            try {

                Class.forName("com.mysql.jdbc.Driver");//连接驱动
                Connection con = DriverManager.getConnection("jdbc:mysql://47.107.128.232/feichai", "root", "123456");//getCon("root","123456");
                uri=Uri.fromFile(Environment.getExternalStorageDirectory()).buildUpon().appendPath(CROP_CACHE_FILE_NAME).build().getPath();
                File image=new File(uri);
                InputStream is=new FileInputStream(image);

                PreparedStatement insert=con.prepareStatement("update user set user_pic=? where user_name=?");

                insert.setBlob(1,is);
                insert.setString(2,user_name);
                insert.setFetchSize(Integer.MIN_VALUE);
                int affect=insert.executeUpdate();
                if(affect==1)
                {
                    dialog.dismiss();
                    System.out.println("图片加入成功");
                    ifupdata=true;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    };


    Runnable runnable=new Runnable() {

        @Override
        public void run() {
            if (ifupdata) {
                Connection con = DatabaseHelper.getCon("root", "123456");
                //Statement stmt = null;

                try {

                    PreparedStatement query = con.prepareStatement("select * from user where user_name='" + user_name + "'");
                    /*query.setInt(0, 0);*/
                    query.setFetchSize(Integer.MIN_VALUE);
                    ResultSet re = query.executeQuery();
                    //把记录指针移到第一行数据（也是唯一的一行）
                    re.next();
                    //使用Blob对象接收数据库里的图片
                    java.sql.Blob imgBlob = re.getBlob(6);
                    //获取Blob对象的二进制流;
                    final InputStream imgInS = imgBlob.getBinaryStream();
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inPreferredConfig = Bitmap.Config.RGB_565;
                    opt.inPurgeable = true;
                    opt.inInputShareable = true;
                    opt.inSampleSize = 2;
                    bitmap = BitmapFactory.decodeStream(imgInS, null, opt);
                    System.out.println("获取头像成功！");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, 50, 50));
                        }
                    });
                    Looper.prepare();
                    System.out.println("显示头像成功！");

                    Looper.loop();
                    re.close();
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                        System.gc();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    };


}
