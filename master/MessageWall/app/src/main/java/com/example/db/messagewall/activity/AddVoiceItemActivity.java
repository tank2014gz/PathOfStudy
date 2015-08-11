package com.example.db.messagewall.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.record.RecordButton;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.utils.PathHelper;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.MaterialDialog;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.support.android.designlibdemo.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AddVoiceItemActivity extends BaseActivity {

    public Toolbar toolbar;

    public RecordButton recordButton;
    public MaterialEditText materialEditText;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    public String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voice_item);

        initToolBar();

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            CONVERSATION_ID = bundle.getString("_ID");
        }

        recordButton = (RecordButton)findViewById(R.id.recordBtn);

        recordButton.setSavePath(PathHelper.getRecordPathByCurrentTime());
        recordButton.setRecordEventListener(new RecordButton.RecordEventListener() {
            @Override
            public void onFinishedRecord(final String audioPath, int secs) throws IOException {

                final MaterialDialog materialDialog = new MaterialDialog(AddVoiceItemActivity.this);
                View view = LayoutInflater.from(AddVoiceItemActivity.this)
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
                                        AVIMAudioMessage avimAudioMessage = new AVIMAudioMessage(audioPath);
                                        avimAudioMessage.setText(title);
                                        AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
                                        avimConversation.sendMessage(avimAudioMessage, new AVIMConversationCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                if (e==null){
                                                    ALifeToast.makeText(AddVoiceItemActivity.this
                                                            , "添加成功！"
                                                            , ALifeToast.ToastType.SUCCESS
                                                            , ALifeToast.LENGTH_SHORT)
                                                            .show();

                                                    Intent intent = new Intent(AddVoiceItemActivity.this,MainActivity.class);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                    AddVoiceItemActivity.this.finish();

                                                }else {
                                                    ALifeToast.makeText(AddVoiceItemActivity.this
                                                            , "添加失败！"
                                                            , ALifeToast.ToastType.SUCCESS
                                                            , ALifeToast.LENGTH_SHORT)
                                                            .show();

                                                    Intent intent = new Intent(AddVoiceItemActivity.this,MainActivity.class);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                    AddVoiceItemActivity.this.finish();

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

            @Override
            public void onStartRecord() {

            }
        });
    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加语音留言条");
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
}
