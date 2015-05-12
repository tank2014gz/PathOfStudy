package com.example.db.civil.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.db.civil.MainActivity;
import com.example.db.civil.R;
import com.example.db.civil.adapter.ArticleAdapter;
import com.example.db.civil.beans.Article;
import com.example.db.civil.beans.ArticleInfo;
import com.example.db.civil.database.ArticleUtlDataBase;
import com.example.db.civil.utlis.AppConstant;
import com.example.db.civil.view.Titanic;
import com.example.db.civil.view.TitanicTextView;
import com.example.db.civil.view.Typefaces;
import com.example.db.civil.view.waveview.WaveView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialnavigationdrawer.util.Utils;

public class WelcomeActivity extends ActionBarActivity {

    public Handler handler;

    public WaveView waveView;

    public Handler temphand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        waveView=(WaveView)findViewById(R.id.wave_view);

        temphand=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x123){
                        List<ArticleInfo> list=(List<ArticleInfo>)msg.obj;

                        ArticleUtlDataBase articleUtlDataBase=new ArticleUtlDataBase(getApplicationContext());
                        SQLiteDatabase sqLiteDatabase=articleUtlDataBase.getWritableDatabase();

                        Cursor cursor=sqLiteDatabase.query("articleurl",new String[]{"url","name"},null,null,null,null,null);

                        if (cursor.getCount()==0){
                            for (int i=0;i<list.size();i++){

                                ContentValues contentValues=new ContentValues();
                                contentValues.put("url",list.get(i).getUrl());
                                contentValues.put("name",list.get(i).getDescription());

                                sqLiteDatabase.insert("articleurl",null,contentValues);
                            }
                        }


                    Log.v("mb",String.valueOf(list.size()));
                }
                else if (msg.what==0x122){

//                    ArrayList<String> temp=new ArrayList<String>();
                    if (AppConstant.list.size()==0) {
                        AppConstant.list = (List<Article>) msg.obj;
                    }

//                    for (int i=0;i<list.size();i++){
//                        temp.add(list.get(i).getUrl().get(0));
//                    }

                    Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                }
            }
        };

        new RemoteDataTask().execute();
        new RemoteDataTask0().execute();

//        handler=new Handler();
//        Runnable runnable=new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        };
//        handler.postDelayed(runnable,500);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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

    private class RemoteDataTask extends AsyncTask<Void, Integer, List<ArticleInfo>> {
        // Override this method to do custom remote calls
        @Override
        protected List<ArticleInfo> doInBackground(Void... params) {

            List<ArticleInfo> list=new ArrayList<ArticleInfo>();


            try {
                Document document= Jsoup.connect("http://www.edu24ol.com/web_news/html/2013-1/201301221029509529.html").get();
                Elements links=document.getElementsByClass("nr_a");
                Elements link=links.get(0).getElementsByClass("nr_ul");





                    for (int i=0;i<link.size();i++){
                        Elements content=link.get(i).getElementsByTag("a");
                        for (int j=0;j<content.size();j++){
                            String articleLink=content.get(j).attr("href");
                            String articleTitle=content.get(j).text();

                            ArticleInfo articleInfo=new ArticleInfo();

                            articleInfo.setUrl("http://www.edu24ol.com"+articleLink);
                            articleInfo.setDescription(articleTitle);

                            list.add(articleInfo);


                            Log.v("info", articleLink);
                            Log.v("info1",articleTitle);
                        }

                    }



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
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
        @Override
        protected void onPostExecute(List<ArticleInfo> result) {
            Message message=Message.obtain();
            message.obj=result;
            message.what=0x123;
            temphand.sendMessage(message);

        }
    }

    private class RemoteDataTask0 extends AsyncTask<Void, Integer, List<Article>> {
        // Override this method to do custom remote calls

        List<ArticleInfo> articleInfos=getArticleInfo();

        List<Article> articles=new ArrayList<Article>();

        public int count=40;

        @Override
        protected List<Article> doInBackground(Void... params) {


            try {

                for (int i=0;i<articleInfos.size();i++){

                    List<String> list=new ArrayList<String>();

                    ArrayList<String> content=new ArrayList<String>();

                    Article article=new Article();
                    Document document=Jsoup.connect(articleInfos.get(i).getUrl()).get();

                    Elements image=document.getElementsByTag("p");

                    for (int j=0;j<image.size();j++){
//                        content.add(image.get(j).html());
                        String link=image.get(i).select("IMG").attr("src").toString();
                        if (link!=null){
                            list.add(link);
                        }
                    }

                    article.setUrl(list);
//                    article.setContent(content);

                    articles.add(article);
                    publishProgress();

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

            for (int t=0;t<articles.size();t++){
                Log.v("uuurl",articles.get(t).getUrl().get(0));
            }


            return articles;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            count+=3;
            if (count<=100){
                waveView.setProgress(count);
            }

        }
        @Override
        protected void onPostExecute(List<Article> result) {
            Message message=Message.obtain();
            message.what=0x122;
            message.obj=result;
            temphand.sendMessage(message);

        }
    }

    public List<ArticleInfo> getArticleInfo(){

        List<ArticleInfo> list=new ArrayList<ArticleInfo>();

        ArticleUtlDataBase articleUtlDataBase=new ArticleUtlDataBase(getApplicationContext());
        SQLiteDatabase sqLiteDatabase=articleUtlDataBase.getWritableDatabase();

        Cursor cursor=sqLiteDatabase.query("articleurl",new String[]{"url","name"},null,null,null,null,null);

        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);

            ArticleInfo articleInfo=new ArticleInfo();
            articleInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            articleInfo.setDescription(cursor.getString(cursor.getColumnIndex("name")));

            list.add(articleInfo);
        }


        return list;
    }
}
