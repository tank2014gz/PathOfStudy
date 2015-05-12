package com.example.db.civil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.db.civil.MainActivity;
import com.example.db.civil.R;
import com.example.db.civil.adapter.StructureAdapter;
import com.example.db.civil.beans.ArticleInfo;
import com.example.db.civil.beans.RuleItem;
import com.example.db.civil.view.views.indicator.Indicator;
import com.example.db.civil.view.views.indicator.IndicatorViewPager;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.JazzyListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialnavigationdrawer.util.Utils;

public class RuleDetailActivity extends ActionBarActivity {


    public JazzyListView jazzyListView;

    public FloatingActionButton floatingActionButton;

    public Button back;

    public TextView kind;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    public ProgressDialog progressDialog;

    private IndicatorViewPager indicatorViewPager;

    public String rulUrl=null;
    public String title=null;

    public Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_detail);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x123){
                    StructureAdapter structureAdapter=new StructureAdapter(getApplicationContext(),(ArrayList<RuleItem>) msg.obj);
                    jazzyListView.setAdapter(structureAdapter);

                }
            }
        };

        title=this.getIntent().getExtras().getString("title");
        rulUrl=this.getIntent().getExtras().getString("ruleurl");


        back=(Button)findViewById(R.id.back);
        kind=(TextView)findViewById(R.id.kind);

        kind.setText(title);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RuleDetailActivity.this, MainActivity.class);
                startActivity(intent);
                RuleDetailActivity.this.finish();
            }
        });

        new RemoteDataTask().execute();


        View mHeadView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.rule_head, null);

        ViewPager viewPager = (ViewPager) mHeadView.findViewById(R.id.guide_viewPager);
        Indicator indicator = (Indicator) mHeadView.findViewById(R.id.guide_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(adapter);


        jazzyListView=(JazzyListView)findViewById(R.id.article_listview);
        jazzyListView.setTransitionEffect(JazzyHelper.TILT);
        jazzyListView.addHeaderView(mHeadView);
        jazzyListView.setHeaderDividersEnabled(false);

        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.article_refreshlayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RemoteDataTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);

        floatingActionButton.attachToListView(jazzyListView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                Log.d("ListViewFragment", "onScrollDown()");
            }

            @Override
            public void onScrollUp() {
                Log.d("ListViewFragment", "onScrollUp()");
            }
        }, new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d("ListViewFragment", "onScrollStateChanged()");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("ListViewFragment", "onScroll()");
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RemoteDataTask().execute();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rule_detail, menu);
        return true;
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

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        private int[] images = { R.drawable.empty, R.drawable.error, R.drawable.head, R.drawable.test };

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_guide, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(getApplicationContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            convertView.setBackgroundResource(images[position]);
            return convertView;
        }

        @Override
        public int getCount() {
            return images.length;
        }
    };

    private class RemoteDataTask extends AsyncTask<Void, Integer, ArrayList<RuleItem>> {
        // Override this method to do custom remote calls

        public ArrayList<RuleItem> ruleItems=new ArrayList<RuleItem>();


        @Override
        protected ArrayList<RuleItem> doInBackground(Void... params) {

            try {
                Log.v("uurr",rulUrl);
                Document document=Jsoup.connect(rulUrl).get();

                Elements elements=document.getElementsByClass("m_g_b_d");

                for (int i=0;i<elements.size();i++){

                    Elements link=elements.get(i).getElementsByTag("li");
                    Log.v("mb4",String.valueOf(link.size()));

                    for (int j=0;j<link.size();j++){

                        String title=link.get(j).getElementsByTag("a").text();
                        String date=link.get(j).text().replace(title,"");
                        String url=link.get(j).getElementsByTag("a").attr("href");

                        Log.v("mba",title+date+url);

                        RuleItem ruleItem=new RuleItem();
                        ruleItem.setUrl(url);
                        ruleItem.setDate(date);
                        ruleItem.setTitle(title);

                        ruleItems.add(ruleItem);

                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            return ruleItems;
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
        protected void onPostExecute(ArrayList<RuleItem> result) {

            Message message=Message.obtain();
            message.what=0x123;
            message.obj=result;
            handler.sendMessage(message);



//            progressDialog.dismiss();
        }
    }
}
