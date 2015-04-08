package com.example.db.civil.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.db.civil.MainActivity;
import com.example.db.civil.R;
import com.example.db.civil.adapter.ArticleAdapter;
import com.example.db.civil.database.ArticleUtlDataBase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import it.neokree.materialnavigationdrawer.util.Utils;

public class WelcomeActivity extends ActionBarActivity {

    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new RemoteDataTask().execute();

        handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        handler.postDelayed(runnable,500);

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

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        // Override this method to do custom remote calls
        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document document= Jsoup.connect("http://www.edu24ol.com/web_news/html/2013-1/201301221029509529.html").get();
                Elements links=document.getElementsByClass("nr_a");
                Elements link=links.get(0).getElementsByClass("nr_ul");

                ArticleUtlDataBase articleUtlDataBase=new ArticleUtlDataBase(getApplicationContext());
                SQLiteDatabase sqLiteDatabase=articleUtlDataBase.getWritableDatabase();
                Cursor cursor=sqLiteDatabase.query("articleurl",new String[]{"url","name"},null,null,null,null,null);

                if (cursor==null){
                    for (int i=0;i<link.size();i++){
                        Elements content=link.get(i).getElementsByTag("a");
                        for (int j=0;j<content.size();j++){
                            String articleLink=content.get(j).attr("href");
                            String articleTitle=content.get(j).text();

                            ContentValues contentValues=new ContentValues();
                            contentValues.put("url","http://www.edu24ol.com"+articleLink);
                            contentValues.put("name",articleTitle);

                            sqLiteDatabase.insert("articleurl",null,contentValues);

                            Log.v("info", articleLink);
                            Log.v("info1",articleTitle);
                        }
                    }
                }



            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
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
        protected void onPostExecute(Void result) {

        }
    }
}
