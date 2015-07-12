package com.example.db.alife.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.db.alife.R;
import com.example.db.alife.adapter.ReadDetailsAdapter;
import com.example.db.alife.adapter.SentenceDetailsAdapter;
import com.example.db.alife.beans.ReadDetailsInfo;
import com.example.db.alife.beans.SentenceDetailsInfo;
import com.example.db.alife.database.ReadDataBaseHelper;
import com.example.db.alife.database.SentenceDataBaseHelper;
import com.example.db.alife.utils.AppConstant;
import com.example.db.alife.view.ALifeToast;
import com.example.db.alife.view.ExpandableTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class SentenceDetailsActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public String title0,picture,url,description,tag;
    public Bundle bundle;

    public ImageView imageView;
    public TextView mTitle;
    public ListView mListView;
    public ExpandableTextView mExpandableTextView;
    public SwipeRefreshLayout mSwipeRefreshLayout;

    public ProgressDialog progressDialog;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public SparseBooleanArray mCollapsedStatus;

    public UMSocialService mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true,this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_details);

        mController = UMServiceFactory.getUMSocialService("com.umeng.share");

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            title0 = bundle.getString("title");
            description = bundle.getString("description");
            picture = bundle.getString("picture");
            url = bundle.getString("url");
            tag = bundle.getString("tag");
        }

        initToolBar();
        initView();

        mController = UMServiceFactory.getUMSocialService("com.umeng.share");

        initToolBar();
        initView();

        mController.setShareContent("我在ALife上看到不错的东西,快来看看吧!");
        mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(SentenceDetailsActivity.this, "1104688317",
                "XFVHbm4rU4SOsOw3");
        qqSsoHandler.addToSocialSDK();

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(SentenceDetailsActivity.this, "1104688317",
                "XFVHbm4rU4SOsOw3");
        qZoneSsoHandler.addToSocialSDK();

        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();

        EmailHandler emailHandler = new EmailHandler();
        smsHandler.addToSocialSDK();

        mController.getConfig().setSsoHandler(qqSsoHandler);
        mController.getConfig().setSsoHandler(qZoneSsoHandler);
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        mController.getConfig().removePlatform(SHARE_MEDIA.WEIXIN);
        mController.getConfig().removePlatform(SHARE_MEDIA.WEIXIN_CIRCLE);


    }

    public void initView(){

        mCollapsedStatus = new SparseBooleanArray();

        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.rule4)
                .showImageForEmptyUri(R.drawable.rule4)
                .showImageOnFail(R.drawable.rule4)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        mListView = (ListView)findViewById(R.id.listview);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshlayout);
        Log.v("mb", "mb");

        View mHeadView = LayoutInflater.from(this).inflate(R.layout.subtitle_item,null);
        mTitle = (TextView)mHeadView.findViewById(R.id.title);
        mExpandableTextView = (ExpandableTextView)mHeadView.findViewById(R.id.expand_text_view);
        imageView = (ImageView)mHeadView.findViewById(R.id.img);

        mTitle.setText(title0);
        mExpandableTextView.setText("   "+description,mCollapsedStatus,0);
        imageLoader.displayImage(picture,imageView,options);

        mListView.addHeaderView(mHeadView);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("zx","zx");
                new RemoteDataTask0().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        new RemoteDataTask0().execute();

        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sentence_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.menu_collection:

                SentenceDataBaseHelper sentenceDataBaseHelper = new SentenceDataBaseHelper(getApplicationContext());
                SQLiteDatabase sqLiteDatabase = sentenceDataBaseHelper.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.query("alife_sentence",new String[]{"title","tag","description","picture","url"},null,null,null,null,null);

                if (AppConstant.isSentenceAgain(sqLiteDatabase, title0)){
                    ALifeToast.makeText(SentenceDetailsActivity.this, "已经收藏过了！", ALifeToast.ToastType.SUCCESS, ALifeToast.LENGTH_SHORT).show();
                }else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("title",title0);
                    contentValues.put("tag",tag);
                    contentValues.put("description",description);
                    contentValues.put("picture",picture);
                    contentValues.put("url",url);

                    sqLiteDatabase.insert("alife_sentence",null,contentValues);
                    sqLiteDatabase.close();
                    ALifeToast.makeText(SentenceDetailsActivity.this, "收藏成功！", ALifeToast.ToastType.SUCCESS, ALifeToast.LENGTH_SHORT).show();
                }

                break;
            case R.id.menu_share:

                mController.openShare(SentenceDetailsActivity.this, false);

                SmsShareContent sms = new SmsShareContent();
                sms.setShareContent("我在ALife上发现了好的内容!");
                mController.setShareMedia(sms);

                TencentWbShareContent tencent = new TencentWbShareContent();
                tencent.setShareContent("我在ALife上发现了好的内容!");
                // 设置tencent分享内容
                mController.setShareMedia(tencent);


                break;

        }


        return super.onOptionsItemSelected(item);
    }
    private class RemoteDataTask0 extends AsyncTask<Void, Integer, ArrayList<SentenceDetailsInfo>> {

        public ArrayList<SentenceDetailsInfo> sentenceDetailsInfos = new ArrayList<SentenceDetailsInfo>();

        @Override
        protected ArrayList<SentenceDetailsInfo> doInBackground(Void... params) {

            try {
                Document document = Jsoup.connect(url).get();
                Elements root = document.getElementsByClass("post_content");
                Elements p = root.get(0).getElementsByTag("p");
                for (int i=3;i<=p.size()-2;i=i+2){
                    String title = p.get(i).text();
                    if (title==null||title.length()==0){
                        title = title0;
                    }
                    String img = p.get(i+1).getElementsByTag("img").attr("src");
                    Log.v("haha",title+" "+img);
                    SentenceDetailsInfo sentenceDetailsInfo = new SentenceDetailsInfo();
                    sentenceDetailsInfo.setTitle(title);
                    sentenceDetailsInfo.setPicture(img);
                    sentenceDetailsInfos.add(sentenceDetailsInfo);
                }

            }catch (Exception e){
                Log.v("error0", e.getMessage());
            }

            return sentenceDetailsInfos;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }
        @Override
        protected void onPostExecute(final ArrayList<SentenceDetailsInfo> result) {
            if (result!=null){
                SentenceDetailsAdapter sentenceDetailsAdapter = new SentenceDetailsAdapter(getApplicationContext(),result);
                mListView.setAdapter(sentenceDetailsAdapter);
                ALifeToast.makeText(SentenceDetailsActivity.this, "加载完毕！", ALifeToast.ToastType.SUCCESS, ALifeToast.LENGTH_SHORT).show();
            }

        }
    }
}