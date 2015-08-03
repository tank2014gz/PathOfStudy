package com.example.db.messagewall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.fragment.MessageWallFragment;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.support.android.designlibdemo.R;

public class AddMessageItemActivity extends BaseActivity {

    public Toolbar toolbar;

    public MaterialEditText mWallContent;
    public Button floatingActionButton;
    public TextView select;
    public TextView select_tuijian;

    public String wallcontent;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message_item);

        initToolBar();

        bundle = this.getIntent().getExtras();
        CONVERSATION_ID = bundle.getString("_ID");

        mWallContent = (MaterialEditText)findViewById(R.id.edit_wall_content);
        floatingActionButton = (Button)findViewById(R.id.btn_fab);
        select = (TextView)findViewById(R.id.select);
        select_tuijian = (TextView)findViewById(R.id.select_tuijian);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wallcontent = mWallContent.getText().toString();

                if (wallcontent!=null||wallcontent.length()!=0){

                    /*
                    发送消息
                     */
                    AVIMTextMessage avimMessage = new AVIMTextMessage();
                    avimMessage.setText(wallcontent);
                    /*
                    通过CONVERSATION_ID找到群组,来通过群组发布消息
                     */
                    AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
                    avimConversation.sendMessage(avimMessage, AVIMConversation.NONTRANSIENT_MESSAGE_FLAG, new AVIMConversationCallback() {
                        @Override
                        public void done(AVException e) {

                            if (null == e) {
                                ALifeToast.makeText(AddMessageItemActivity.this
                                        , "添加成功！"
                                        , ALifeToast.ToastType.SUCCESS
                                        , ALifeToast.LENGTH_SHORT)
                                        .show();

                                Intent intent = new Intent(AddMessageItemActivity.this,MainActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                AddMessageItemActivity.this.finish();

                            } else {
                                ALifeToast.makeText(AddMessageItemActivity.this
                                        , "添加失败！"
                                        , ALifeToast.ToastType.SUCCESS
                                        , ALifeToast.LENGTH_SHORT)
                                        .show();

                                Intent intent = new Intent(AddMessageItemActivity.this,MainActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                AddMessageItemActivity.this.finish();

                                Log.v("db.error5", e.getMessage());
                            }
                        }
                    });

                }else {
                    ALifeToast.makeText(AddMessageItemActivity.this
                            , "输入不能为空！"
                            , ALifeToast.ToastType.SUCCESS
                            , ALifeToast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });

        select_tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMessageItemActivity.this,SelectPaperActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加文字留言条");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==2&&resultCode== Activity.RESULT_OK){
            if (data!=null){
                Uri uri = data.getData();
                String path = AppConstant.getPath(AddMessageItemActivity.this,uri);
                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences("com.example.db.alife_wallitempaper"
                                , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("paper_path", path);
                editor.commit();
                Log.v("paper_path", path);
            }else {
                AppConstant.showSelfToast(AddMessageItemActivity.this,"选择失败！");
            }
        }
    }
}
