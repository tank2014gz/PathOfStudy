package com.example.db.messagewall.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.db.messagewall.adapter.AlterWallAdapter;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.AppConstant;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

public class AlertWallActivity extends AppCompatActivity {

    public Toolbar toolbar;

    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_wall);

        initToolBar();

        recyclerView = (RecyclerView)findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        /*
        直接加载所有的留言墙
         */
        List<String> list = new ArrayList<String>();
        list.add(AVUser.getCurrentUser().getUsername());

        AppData.setClientIdToPre(AVUser.getCurrentUser().getUsername());

        final AVIMClient avimClient0 = AppData.getIMClient();
        final List<String> queryClientIds = new ArrayList<String>();
        queryClientIds.addAll(list);

        avimClient0.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVException e) {
                if (e == null) {
                    AVIMConversationQuery query = avimClient.getQuery();
                    query.containsMembers(queryClientIds);
                    query.findInBackground(new AVIMConversationQueryCallback() {
                        @Override
                        public void done(List<AVIMConversation> list, AVException e) {
                            if (null != e) {
                                Log.v("db.error4", e.getMessage());
                            } else {
                                recyclerView.setAdapter(new AlterWallAdapter(AlertWallActivity.this, list));
                                Log.v("db.cnm2", String.valueOf(list.size()));
                            }
                        }
                    });
                } else {
                    Log.v("db.error11", e.getMessage());
                }
            }
        });
    }
    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("切换留言墙");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alert_wall, menu);
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
