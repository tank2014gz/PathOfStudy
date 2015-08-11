package com.example.db.messagewall.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.db.messagewall.record.PlayButton;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.utils.DownloadFileRunnable;
import com.example.db.messagewall.utils.DownloadRunnable0;
import com.example.db.messagewall.utils.PathHelper;
import com.support.android.designlibdemo.R;

import java.util.List;


public class VoiceDetailsActivity extends BaseActivity {

    public Toolbar toolbar;

    public Bundle bundle;

    public String content,date,from,url,msgId,size,duration,nichen;

    public TextView mContent,mDate,mFrom,mSize,mDuration,mNiChen;
    public PlayButton playButton;

    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_details);

        initToolBar();

        mContent = (TextView)findViewById(R.id.content);
        mDate = (TextView)findViewById(R.id.date);
        mFrom = (TextView)findViewById(R.id.from);
        mSize = (TextView)findViewById(R.id.size);
        mDuration = (TextView)findViewById(R.id.duration);
        mNiChen = (TextView)findViewById(R.id.nichen);
        playButton = (PlayButton)findViewById(R.id.playBtn);

        /*
        给textview设置下划线
         */
        mContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            content = bundle.getString("content");
            date = bundle.getString("date");
            from = bundle.getString("from");
            url = bundle.getString("url");
            msgId = bundle.getString("msgId");
            size = bundle.getString("size");
            duration = bundle.getString("long");
            nichen = bundle.getString("nichen");
            Log.v("mlgeb",url);
        }

        if (content!=null&&content.length()!=0){
            mContent.setText(content+".wav");
            mDate.setText("时间: "+date);
            mFrom.setText("来自: "+from);
            mSize.setText("大小: "+size);
            mDuration.setText("长度: "+duration);
        }

        /*
        显示昵称
         */
        AVQuery<AVObject> query = new AVQuery<AVObject>("NiChen");
        query.whereEqualTo("username", from);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){
                    AVObject avObject = (AVObject)list.get(0);
                    if (avObject.get("nichen").toString()!=null){
                        mNiChen.setText("昵称: "+avObject.get("nichen").toString());
                    }else {
                        mNiChen.setText("昵称: "+from);
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });

        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,link);
                VoiceDetailsActivity.this.startActivity(intent);
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x123){
                    Log.v("wwwwwww",(String)msg.obj);
                    playButton.setLeftSide(true);
                    playButton.setPath((String)msg.obj);
                }else if(msg.what==0x122){
                    AppConstant.showSelfToast(VoiceDetailsActivity.this,"获取语音消息失败！");
                }
            }
        };

        new Thread(new DownloadFileRunnable(handler,url,content)).start();

    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("详情");
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
