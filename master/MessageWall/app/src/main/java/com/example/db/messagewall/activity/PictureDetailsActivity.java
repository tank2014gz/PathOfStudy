package com.example.db.messagewall.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.messagewall.utils.AppConstant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.support.android.designlibdemo.R;

public class PictureDetailsActivity extends BaseActivity {

    public Toolbar toolbar;

    public Bundle bundle;

    public String content,date,from,url;

    public TextView mContent,mDate,mFrom;
    public ImageView imageView;
    public Button mDownload;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_details);

        initToolBar();

        mContent = (TextView)findViewById(R.id.content);
        mDate = (TextView)findViewById(R.id.date);
        mFrom = (TextView)findViewById(R.id.from);
        imageView = (ImageView)findViewById(R.id.img);
        mDownload = (Button)findViewById(R.id.btn_download);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(PictureDetailsActivity.this));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.photo3)
                .showImageForEmptyUri(R.drawable.photo3)
                .showImageOnFail(R.drawable.photo3)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            content = bundle.getString("content");
            date = bundle.getString("date");
            from = bundle.getString("from");
            url = bundle.getString("url");
        }

        if (content!=null&&content.length()!=0){
            mContent.setText("标题: "+content);
            mDate.setText("时间: "+date);
            mFrom.setText("来自: "+from);
        }

        if (url!=null){
            imageLoader.displayImage(url,imageView,options);
        }

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
