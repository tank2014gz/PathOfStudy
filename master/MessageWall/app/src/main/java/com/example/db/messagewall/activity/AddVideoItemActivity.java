package com.example.db.messagewall.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMVideoMessage;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.CircleButton;
import com.example.db.messagewall.view.MaterialDialog;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.support.android.designlibdemo.R;

import java.io.IOException;

public class AddVideoItemActivity extends BaseActivity {

    public Toolbar toolbar;

    public CircleButton circleButton;

    public MaterialEditText materialEditText;

    public String title;

    public String path;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video_item);

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            CONVERSATION_ID = bundle.getString("_ID");
        }

        initToolBar();

        circleButton = (CircleButton)findViewById(R.id.circlebutton);

        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                调用系统的录制视频的功能
                 */
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, 2);

            }
        });

    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加短视频留言条");
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
                path = AppConstant.getPath(AddVideoItemActivity.this,uri);

                if (path!=null&&path.length()!=0){
                    final MaterialDialog materialDialog = new MaterialDialog(AddVideoItemActivity.this);
                    View view = LayoutInflater.from(AddVideoItemActivity.this)
                            .inflate(R.layout.scanner_iuput,null);
                    materialEditText = (MaterialEditText)view.findViewById(R.id.edit_ask_phone);
                    materialDialog.setView(view);
                    materialDialog.setCanceledOnTouchOutside(true)
                            .setPositiveButton("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    title = materialEditText.getText().toString();
                                    if (title!=null&&title.length()!=0){
                                        try {
                                            AVIMVideoMessage avimVideoMessage = new AVIMVideoMessage(path);
                                            avimVideoMessage.setText(title);
                                            AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
                                            avimConversation.sendMessage(avimVideoMessage, new AVIMConversationCallback() {
                                                @Override
                                                public void done(AVException e) {
                                                    if (e==null){
                                                        ALifeToast.makeText(AddVideoItemActivity.this
                                                                , "添加成功！"
                                                                , ALifeToast.ToastType.SUCCESS
                                                                , ALifeToast.LENGTH_SHORT)
                                                                .show();

                                                        Intent intent = new Intent(AddVideoItemActivity.this,MainActivity.class);
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                        AddVideoItemActivity.this.finish();

                                                    }else {
                                                        ALifeToast.makeText(AddVideoItemActivity.this
                                                                , "添加失败！"
                                                                , ALifeToast.ToastType.SUCCESS
                                                                , ALifeToast.LENGTH_SHORT)
                                                                .show();

                                                        Intent intent = new Intent(AddVideoItemActivity.this,MainActivity.class);
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                        AddVideoItemActivity.this.finish();

                                                        Log.v("db.error5", e.getMessage());
                                                    }
                                                }
                                            });
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    materialDialog.dismiss();
                                }
                            })
                            .setNegativeButton("Cancel", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    materialDialog.dismiss();
                                }
                            });
                    materialDialog.show();
                }

                Log.v("paper_path", path);

            }else {
                AppConstant.showSelfToast(AddVideoItemActivity.this,"选择失败！");
            }
        }
    }
}
