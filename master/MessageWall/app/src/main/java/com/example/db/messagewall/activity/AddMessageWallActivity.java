package com.example.db.messagewall.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.support.android.designlibdemo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMessageWallActivity extends AppCompatActivity {

    public Toolbar toolbar;

    public MaterialEditText mWallName,mNiChen,mWallDescription;
    public FloatingActionButton mPutForward;

    public String wall_name,wall_nichen,wall_description,friends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message_wall);

        initToolBar();

        mWallName = (MaterialEditText)findViewById(R.id.edit_wall_name);
        mNiChen = (MaterialEditText)findViewById(R.id.edit_wall_nichen);
        mWallDescription = (MaterialEditText)findViewById(R.id.edit_wall_description);
        mPutForward = (FloatingActionButton)findViewById(R.id.add);


        mPutForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wall_name = mWallName.getText().toString();
                wall_nichen = mNiChen.getText().toString();
                wall_description = mWallDescription.getText().toString();
                if (wall_name.length()!=0&&wall_nichen.length()!=0&&wall_description.length()!=0){
                        AppData.setClientIdToPre(AVUser.getCurrentUser().getUsername());
                        AVIMClient avimClient = AppData.getIMClient();
                        avimClient.open(new AVIMClientCallback() {
                            @Override
                            public void done(final AVIMClient avimClient, AVException e) {
                                if (e==null){
                                    /*
                                    添加成员，默认的只有群主自己
                                     */
                                    List<String> clientIds = new ArrayList<String>();
                                    clientIds.add(AVUser.getCurrentUser().getUsername());
                                    /*
                                    默认的群信息，包括描述，创建时间，创建的昵称等等
                                     */
                                    Map<String, Object> attr = new HashMap<String, Object>();
                                    attr.put("type", AppConstant.ConversationType_Group);
                                    attr.put("description",wall_description);
                                    attr.put("date",AppConstant.getCurrentTime());
                                    attr.put("nichen",wall_nichen);
                                    attr.put("name",wall_name);

                                    avimClient.createConversation(clientIds, attr, new AVIMConversationCreatedCallback() {
                                        @Override
                                        public void done( final AVIMConversation conversation, AVException e) {
                                            if (null != conversation) {
                                                /*
                                                成功了,并且生成二维码，将二维码上传到leancloud上去
                                                 */
                                                Bitmap bitmap = AppConstant.createQRImage(conversation.
                                                        getConversationId() +
                                                        " " +
                                                        conversation.getCreator());
                                                String path = AppConstant.saveQRImage(bitmap, wall_name);
                                                /*
                                                通过上传本地图片文件
                                                获取二维码的url
                                                更新conversation的attribute属性
                                                添加link_url属性
                                                 */
                                                try {
                                                    final AVFile avFile = AVFile.withAbsoluteLocalPath(wall_name+".png",path);
                                                    avFile.saveInBackground(new SaveCallback() {
                                                        @Override
                                                        public void done(AVException e) {
                                                            if (e==null){
                                                                conversation.setAttribute("link_url",avFile.getUrl());
                                                                Log.v("lj",avFile.getUrl());
                                                                conversation.updateInfoInBackground(new AVIMConversationCallback() {
                                                                    @Override
                                                                    public void done(AVException e) {
                                                                        if (e==null){

                                                                        }else {
                                                                            Log.v("db.error9",e.getMessage());
                                                                        }
                                                                    }
                                                                });
                                                            }else {
                                                                Log.v("db.error10",e.getMessage());
                                                            }
                                                        }
                                                    });


                                                }catch (Exception e0){
                                                    Log.v("db.error8",e0.getMessage());
                                                }

                                                ALifeToast.makeText(AddMessageWallActivity.this
                                                        , "创建成功！"
                                                        , ALifeToast.ToastType.SUCCESS
                                                        , ALifeToast.LENGTH_SHORT)
                                                        .show();

                                                Intent intent = new Intent(AddMessageWallActivity.this, SelectActivity.class);
                                                startActivity(intent);
                                                AddMessageWallActivity.this.finish();
                                            }else {
                                                ALifeToast.makeText(AddMessageWallActivity.this
                                                        , "创建失败！"
                                                        , ALifeToast.ToastType.SUCCESS
                                                        , ALifeToast.LENGTH_SHORT)
                                                        .show();
                                                Intent intent = new Intent(AddMessageWallActivity.this, SelectActivity.class);
                                                startActivity(intent);
                                                AddMessageWallActivity.this.finish();

                                            }
                                        }
                                    });

                                }else {
                                    ALifeToast.makeText(AddMessageWallActivity.this
                                            , "创建失败！"
                                            , ALifeToast.ToastType.SUCCESS
                                            , ALifeToast.LENGTH_SHORT)
                                            .show();

                                }
                            }
                        });

                }else {
                    ALifeToast.makeText(AddMessageWallActivity.this
                            , "输入不能为空！"
                            , ALifeToast.ToastType.SUCCESS
                            , ALifeToast.LENGTH_SHORT)
                            .show();

                }
            }
        });

    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加一面墙");
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
    public Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_add_message_wall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
