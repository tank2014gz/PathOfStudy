package com.example.db.messagewall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.CircleImageView;
import com.example.db.messagewall.view.MaterialDialog;
import com.support.android.designlibdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersonInfoActivity extends BaseActivity {

    public Toolbar toolbar;

    public CircleImageView circleImageView;
    public TextView mNiChen, mPhone, mDate;
    public Button mLoginOut;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        initToolBar();

        bundle = this.getIntent().getExtras();
        CONVERSATION_ID = bundle.getString("_ID");

        circleImageView = (CircleImageView) findViewById(R.id.person_logo);
        mNiChen = (TextView) findViewById(R.id.nichen);
        mPhone = (TextView) findViewById(R.id.phone);
        mDate = (TextView) findViewById(R.id.date);
        mLoginOut = (Button)findViewById(R.id.btn_loginout);

        /*
        显示用户设置的logo
         */
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.db.alife_walllogo", Context.MODE_PRIVATE);
        String paper = sharedPreferences.getString("paper_path","");
        if(paper.equals("")){
            circleImageView.setBackgroundResource(R.drawable.ic_launcher);
        }else {
            Bitmap bitmap = BitmapFactory.decodeFile(paper);
            circleImageView.setImageBitmap(bitmap);
        }

        final AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
        avimConversation.fetchInfoInBackground(new AVIMConversationCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    mNiChen.setText(avimConversation.getAttribute("nichen").toString());
                    mDate.setText(avimConversation.getAttribute("date").toString());
                    mPhone.setText(avimConversation.getCreator());
                } else {
                    Log.v("db.error17", e.getMessage());
                }
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog materialDialog = new MaterialDialog(PersonInfoActivity.this);
                View view = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.logo_select, null);
                TextView camera = (TextView) view.findViewById(R.id.scanner);
                TextView photo = (TextView) view.findViewById(R.id.add);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        拍照取图片
                         */
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                        directory.mkdir();
                        File QR = new File(directory.getAbsolutePath() + "/MessageWall/logo");
                        if (!QR.exists()) {
                            QR.mkdir();
                        }
                        String path = QR.getAbsolutePath() +"/"+AVUser.getCurrentUser().getUsername() + ".png";
                        File file = new File(path);
                        if (!file.exists()){
                            file.mkdir();
                        }
                        Uri imgUri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        startActivityForResult(intent, 4);
                        materialDialog.dismiss();
                    }
                });
                photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        从图库中选择
                         */
                        cutPicture();
                        materialDialog.dismiss();
                    }
                });
                materialDialog.setView(view)
                        .setCanceledOnTouchOutside(true);
                materialDialog.show();
            }
        });

        mLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonInfoActivity.this,SignInActivity.class));
                PersonInfoActivity.this.finish();
            }
        });

    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("账户信息");
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));

        if (Build.VERSION.SDK_INT >= 21)
            toolbar.setElevation(24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 4 && resultCode == RESULT_OK) {
            /*
            注意此时的data为空，不能直接对data进行操作，直接从本地的文件中操作.
             */
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            directory.mkdir();
            File QR = new File(directory.getAbsolutePath() + "/MessageWall/logo");
            if (!QR.exists()) {
                QR.mkdir();
            }
            String path = QR.getAbsolutePath() +"/"+AVUser.getCurrentUser().getUsername() + ".png";
            File file = new File(path);
            if (!file.exists()){
                file.mkdir();
            }
            Uri imgUri = Uri.fromFile(file);
            cutPicture0(imgUri);
        }else if (requestCode==5 && resultCode==RESULT_OK){
            if (data!=null){
                /*
                处理裁剪后的结果
                 */
                Bundle bundle = data.getExtras();
                if (bundle!=null){
                    Bitmap photo = bundle.getParcelable("data");
                    /*
                    存储到本地
                     */
                    Log.v("haahha",savePicture(photo));
                    SharedPreferences sharedPreferences = this
                            .getSharedPreferences("com.example.db.alife_walllogo"
                                    , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("paper_path", savePicture(photo));
                    editor.commit();
                    circleImageView.setImageBitmap(photo);
                }else {

                }
            }
        }
    }

    /*
    调用图库时系统的裁剪功能
     */
    public void cutPicture(){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 5);
    }
    /*
    调用相机时
     */
    public void cutPicture0(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 5);
    }
    public String savePicture(Bitmap bitmap){

        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR = new File(directory.getAbsolutePath() + "/MessageWall/logo");
        if (!QR.exists()) {
            QR.mkdir();
        }
        String path = QR.getAbsolutePath() +"/"+AVUser.getCurrentUser().getUsername() + ".png";
        File file = new File(path);
        try
        {
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
