package com.example.db.alife.activity;

import android.app.ProgressDialog;
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
import com.example.db.alife.adapter.EnglishMotoAdapter;
import com.example.db.alife.adapter.MotoDetailsAdapter;
import com.example.db.alife.beans.EnglishMotoInfo;
import com.example.db.alife.beans.MotodetailsInfo;
import com.example.db.alife.utils.AppConstant;
import com.example.db.alife.view.ALifeToast;
import com.example.db.alife.view.ExpandableTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MotoDetailsActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public String title,picture,url,description;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true,this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moto_details);

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            title = bundle.getString("title");
            description = bundle.getString("description");
            picture = bundle.getString("picture");
            url = bundle.getString("url");
        }

        initToolBar();
        initView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_moto_details, menu);
        return true;
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
        Log.v("mb","mb");

        View mHeadView = LayoutInflater.from(this).inflate(R.layout.subtitle_item,null);
        mTitle = (TextView)mHeadView.findViewById(R.id.title);
        mExpandableTextView = (ExpandableTextView)mHeadView.findViewById(R.id.expand_text_view);
        imageView = (ImageView)mHeadView.findViewById(R.id.img);

        mTitle.setText(title);
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
    public Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class RemoteDataTask0 extends AsyncTask<Void, Integer, ArrayList<MotodetailsInfo>> {

        public ArrayList<MotodetailsInfo> motodetailsInfos = new ArrayList<MotodetailsInfo>();

        @Override
        protected ArrayList<MotodetailsInfo> doInBackground(Void... params) {

            try {
                Document document = Jsoup.connect(url).get();
                Elements root = document.getElementsByClass("post_content");
                Elements p = root.get(0).getElementsByTag("p");
                for (int i=3;i<=p.size()-2;i=i+2){
                    String english = p.get(i).text();
                    String chinese = p.get(i+1).text();
                    MotodetailsInfo motodetailsInfo = new MotodetailsInfo();
                    motodetailsInfo.setEnglish(english);
                    motodetailsInfo.setChinese(chinese);
                    motodetailsInfos.add(motodetailsInfo);
                }

            }catch (Exception e){
                Log.v("error0", e.getMessage());
            }

            return motodetailsInfos;
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
        protected void onPostExecute(final ArrayList<MotodetailsInfo> result) {
            if (result!=null){
                MotoDetailsAdapter motoDetailsAdapter = new MotoDetailsAdapter(getApplicationContext(),result);
                mListView.setAdapter(motoDetailsAdapter);
                ALifeToast.makeText(MotoDetailsActivity.this, "加载完毕！", ALifeToast.ToastType.SUCCESS, ALifeToast.LENGTH_SHORT).show();
            }

        }
    }
}
