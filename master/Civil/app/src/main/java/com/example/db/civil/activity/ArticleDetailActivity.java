package com.example.db.civil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.db.civil.MainActivity;
import com.example.db.civil.R;
import com.example.db.civil.view.views.indicator.Indicator;
import com.example.db.civil.view.views.indicator.IndicatorViewPager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.markdown4j.Markdown4jProcessor;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialnavigationdrawer.util.Utils;

public class ArticleDetailActivity extends ActionBarActivity {

    public WebView textView;
    public WebSettings webSettings;

    public TextView description;

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    public ProgressDialog progressDialog;

    public String url;

    public Handler handler;

    public Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        description=(TextView)findViewById(R.id.description);

        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ArticleDetailActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        Indicator indicator = (Indicator) findViewById(R.id.guide_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());
        indicatorViewPager.setAdapter(adapter);

        Bundle bundle=this.getIntent().getExtras();

        url=bundle.getString("articleurl");

        String des=bundle.getString("description");
        description.setText(des);

        textView=(WebView)findViewById(R.id.content);

        webSettings=textView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(false);
        webSettings.setBlockNetworkImage(true);

        new RemoteDataTask().execute();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x123){
                    try{
                        String html=new Markdown4jProcessor().addHtmlAttribute("target","_blank","a","p","strong","img").process(handString((ArrayList<String>)msg.obj));
                        textView.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };


    }

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        private int[] images = { R.drawable.empty, R.drawable.error, R.drawable.head, R.drawable.test };

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_guide, container, false);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_detail, menu);
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

    public String handString(ArrayList<String> list){

        String result=null;

        for (int i=0;i<list.size();i++){
            result=result+"    "+list.get(i)+"<br/>";
        }

        return result;
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, ArrayList<String>> {
        // Override this method to do custom remote calls


        ArrayList<String> list=new ArrayList<String>();

        @Override
        protected ArrayList<String> doInBackground(Void... params) {

            try {

                    Document document= Jsoup.connect(url).get();

                    Elements image=document.getElementsByTag("p");

                    for (int j=0;j<image.size();j++){
                        list.add(image.get(j).html());
                    }



                /**
                 * 测试
                 *Document document= Jsoup.connect("http://www.edu24ol.com/web_news/html/2013-1/201301221029509529.html").get();
                 *Elements image=document.getElementsByTag("p");
                 *for (int i=0;i<image.size();i++){
                 Log.v("image",image.get(i).text());
                 String link=image.get(i).select("IMG").attr("src").toString();
                 Log.v("url",link);
                 *}
                 **/

            }catch (Exception e) {
                e.printStackTrace();
            }



            return list;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(ArrayList<String> result) {

            Message message=Message.obtain();
            message.obj=result;
            message.what=0x123;
            handler.sendMessage(message);

        }
    }

}
