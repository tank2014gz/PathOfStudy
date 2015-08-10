package com.example.db.messagewall.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.messages.AVIMFileMessage;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.support.android.designlibdemo.R;

import java.io.IOException;

public class AddFileItemActivity extends BaseActivity {

    public Toolbar toolbar;

    public TextView select;
    public Button putforward;
    public MaterialEditText materialEditText;

    public String content;
    public String path;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file_item);

        initToolBar();

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            CONVERSATION_ID = bundle.getString("_ID");
        }

        select = (TextView)findViewById(R.id.select);
        putforward = (Button)findViewById(R.id.btn_fab);
        materialEditText = (MaterialEditText)findViewById(R.id.edit_wall_content);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("file/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });

        putforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = materialEditText.getText().toString();
                if (content!=null&&content.length()!=0&&path!=null&&path.length()!=0){
                    try {
                        AVIMFileMessage avimFileMessage = new AVIMFileMessage(path);
                        avimFileMessage.setText(content);
                        AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
                        avimConversation.sendMessage(avimFileMessage, new AVIMConversationCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e==null){
                                    ALifeToast.makeText(AddFileItemActivity.this
                                            , "添加成功！"
                                            , ALifeToast.ToastType.SUCCESS
                                            , ALifeToast.LENGTH_SHORT)
                                            .show();

                                    Intent intent = new Intent(AddFileItemActivity.this,MainActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    AddFileItemActivity.this.finish();

                                }else {
                                    ALifeToast.makeText(AddFileItemActivity.this
                                            , "添加失败！"
                                            , ALifeToast.ToastType.SUCCESS
                                            , ALifeToast.LENGTH_SHORT)
                                            .show();

                                    Intent intent = new Intent(AddFileItemActivity.this,MainActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    AddFileItemActivity.this.finish();

                                    Log.v("db.error5", e.getMessage());
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加文件留言条");
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
                path = uri.getPath();

                Log.v("paper_path", path);
            }else {
                AppConstant.showSelfToast(AddFileItemActivity.this,"选择失败！");
            }
        }
    }
}
