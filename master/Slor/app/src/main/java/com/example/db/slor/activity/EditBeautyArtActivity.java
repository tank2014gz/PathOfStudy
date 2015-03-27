package com.example.db.slor.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.db.slor.R;
import com.example.db.slor.service.MyService;
import com.example.db.slor.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditBeautyArtActivity extends ActionBarActivity {

    public Button mArticleBack,mArticleSend;
    public EditText mArticleTitle,mArticleContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_beauty_art);

        mArticleBack=(Button)findViewById(R.id.article_back);
        mArticleSend=(Button)findViewById(R.id.article_send);

        mArticleTitle=(EditText)findViewById(R.id.article_title);
        mArticleContent=(EditText)findViewById(R.id.article_content);

        mArticleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(EditBeautyArtActivity.this,NavigatorDrawerActivity.class);
                startActivity(intent);
                EditBeautyArtActivity.this.finish();
            }
        });

        mArticleSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SaveCallback saveCallback=new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        // done方法一定在UI线程执行
                        if (e == null) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("success", e == null);
                            Intent intent = new Intent();
                            intent.putExtras(bundle);
                            intent.setClass(EditBeautyArtActivity.this,NavigatorDrawerActivity.class);
                            startActivity(intent);
                            EditBeautyArtActivity.this.finish();

                            Log.v("sbbbbbbbbbbbb1", "s");

                        } else {
                            Log.v("sbbbbbbbbbbbbbbb2", e.getMessage());
                        }


                    }
                };
                String title=mArticleTitle.getText().toString();
                String content=mArticleContent.getText().toString();

                //获取当前时间
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String date= formatter.format(curDate);

                MyService.createOrUpdateTodo(title,content,AVUser.getCurrentUser(),date,true,true,saveCallback);



            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_beauty_art, menu);
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
}
