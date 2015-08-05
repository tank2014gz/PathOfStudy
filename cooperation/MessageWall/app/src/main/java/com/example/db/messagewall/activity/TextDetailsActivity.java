package com.example.db.messagewall.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.db.messagewall.api.MessageHandlerHelper;
import com.example.db.messagewall.utils.AppConstant;
import com.support.android.designlibdemo.R;

public class TextDetailsActivity extends BaseActivity {

    public Toolbar toolbar;

    public Bundle bundle;

    public String content,date,from;

    public TextView mContent,mDate,mFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_details);

        initToolBar();

        mContent = (TextView)findViewById(R.id.content);
        mDate = (TextView)findViewById(R.id.date);
        mFrom = (TextView)findViewById(R.id.from);

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            content = bundle.getString("content");
            date = bundle.getString("date");
            from = bundle.getString("from");
        }

        if (content!=null&&content.length()!=0){
            mContent.setText("内容: "+content);
            mDate.setText("时间: "+date);
            mFrom.setText("来自: "+from);
        }
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
