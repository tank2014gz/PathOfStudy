package com.example.db.messagewall.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.db.messagewall.utils.AppConstant;
import com.support.android.designlibdemo.R;

public class FileDetailsActivity extends BaseActivity {

    public Toolbar toolbar;

    public Bundle bundle;

    public String content,date,from,url;

    public TextView mContent,mDate,mFrom;
    public Button mDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_details);

        initToolBar();

        mContent = (TextView)findViewById(R.id.content);
        mDate = (TextView)findViewById(R.id.date);
        mFrom = (TextView)findViewById(R.id.from);
        mDownload = (Button)findViewById(R.id.btn_download);

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

        }

        if (content!=null&&content.length()!=0){
            mContent.setText(content);
            mDate.setText("时间: "+date);
            mFrom.setText("来自: "+from);
        }

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,link);
                FileDetailsActivity.this.startActivity(intent);
            }
        });
    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("详情");
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
}
