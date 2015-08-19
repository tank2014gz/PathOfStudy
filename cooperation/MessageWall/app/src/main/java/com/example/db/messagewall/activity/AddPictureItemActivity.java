package com.example.db.messagewall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.dd.CircularProgressButton;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.example.db.messagewall.view.swipebacklayout.SwipeBackActivity;
import com.example.db.messagewall.view.swipebacklayout.SwipeBackLayout;
import com.support.android.designlibdemo.R;

import java.io.IOException;

public class AddPictureItemActivity extends SwipeBackActivity {

    public Toolbar toolbar;

    public TextView select;
//    public Button putforward;
    public MaterialEditText materialEditText;

    public CircularProgressButton circularProgressButton;

    public String content;
    public String path;

    public Bundle bundle;
    public static String CONVERSATION_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture_item);

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            CONVERSATION_ID = bundle.getString("_ID");
        }


        initToolBar();

        select = (TextView)findViewById(R.id.select);
//        putforward = (Button)findViewById(R.id.btn_fab);
        materialEditText = (MaterialEditText)findViewById(R.id.edit_wall_content);

        circularProgressButton = (CircularProgressButton)findViewById(R.id.circularButton);
        circularProgressButton.setIndeterminateProgressMode(true);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });

        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (circularProgressButton.getProgress()==0){
                    circularProgressButton.setProgress(50);

                    content = materialEditText.getText().toString();
                    if (content!=null&&content.length()!=0&&path!=null&&path.length()!=0){
                        try {
                            AVIMImageMessage avimImageMessage = new AVIMImageMessage(path);
                            avimImageMessage.setText(content);
                            AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
                            avimConversation.sendMessage(avimImageMessage, new AVIMConversationCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e==null){
//                                        ALifeToast.makeText(AddPictureItemActivity.this
//                                                , "添加成功！"
//                                                , ALifeToast.ToastType.SUCCESS
//                                                , ALifeToast.LENGTH_SHORT)
//                                                .show();

                                        circularProgressButton.setProgress(100);
//                                    Intent intent = new Intent(AddPictureItemActivity.this,MainActivity.class);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                    AddPictureItemActivity.this.finish();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(AddPictureItemActivity.this,MainActivity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                AddPictureItemActivity.this.finish();
                                            }
                                        },1000);
                                    }else {

                                        circularProgressButton.setProgress(0);

                                        ALifeToast.makeText(AddPictureItemActivity.this
                                                , "添加失败！"
                                                , ALifeToast.ToastType.SUCCESS
                                                , ALifeToast.LENGTH_SHORT)
                                                .show();

//                                    Intent intent = new Intent(AddPictureItemActivity.this,MainActivity.class);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                    AddPictureItemActivity.this.finish();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(AddPictureItemActivity.this,MainActivity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                AddPictureItemActivity.this.finish();
                                            }
                                        },1000);

                                        Log.v("db.error5", e.getMessage());
                                    }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else if(circularProgressButton.getProgress()==100){

                    Intent intent = new Intent(AddPictureItemActivity.this,MainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    AddPictureItemActivity.this.finish();
                }

            }
        });
    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加图片留言条");
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));

        if (Build.VERSION.SDK_INT >= 21)
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==2&&resultCode== Activity.RESULT_OK){
            if (data!=null){
                Uri uri = data.getData();
                path = AppConstant.getPath(AddPictureItemActivity.this,uri);

                Log.v("paper_path", path);
            }else {
                AppConstant.showSelfToast(AddPictureItemActivity.this,"选择失败！");
            }
        }
    }
}
