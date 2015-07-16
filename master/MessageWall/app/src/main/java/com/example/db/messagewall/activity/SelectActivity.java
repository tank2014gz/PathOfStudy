package com.example.db.messagewall.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.db.messagewall.adapter.WallAdapter;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.AppConstant;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";

    public RecyclerView recyclerView;
    public FloatingActionButton floatingActionButton;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        final String cheeseName = "留言墙";
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitle(cheeseName);

        loadBackdrop();

        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshlayout);
        recyclerView = (RecyclerView)findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        floatingActionButton = (FloatingActionButton)findViewById(R.id.add);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*
                刷新加载
                 */
                List<String> list = new ArrayList<String>();
                list.add(AVUser.getCurrentUser().getUsername());

                final AVIMClient avimClient0 = AppData.getIMClient();
                final List<String> queryClientIds = new ArrayList<String>();
                queryClientIds.addAll(list);

                AVIMConversationQuery query = avimClient0.getQuery();
                query.containsMembers(queryClientIds);
                query.findInBackground(new AVIMConversationQueryCallback() {
                    @Override
                    public void done(List<AVIMConversation> list, AVException e) {
                        if (null!=e){
                            Log.v("db.error4",e.getMessage());
                        }else {
                            recyclerView.setAdapter(new WallAdapter(getApplicationContext(),list));
                            Log.v("db.cnm2", String.valueOf(list.size()));
                        }
                    }
                });
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVUser avUser = AVUser.getCurrentUser();
                if (avUser!=null){
                    Intent intent = new Intent(SelectActivity.this,AddMessageWallActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SelectActivity.this,SignUpActivity.class);
                    startActivity(intent);
                }
            }
        });

        /*
        刷新加载
         */
        List<String> list = new ArrayList<String>();
        list.add(AVUser.getCurrentUser().getUsername());

        final AVIMClient avimClient0 = AppData.getIMClient();
        final List<String> queryClientIds = new ArrayList<String>();
        queryClientIds.addAll(list);

        AVIMConversationQuery query = avimClient0.getQuery();
        query.containsMembers(queryClientIds);
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVException e) {
                if (null!=e){
                    Log.v("db.error4",e.getMessage());
                }else {
                    recyclerView.setAdapter(new WallAdapter(getApplicationContext(),list));
                    Log.v("db.cnm2", String.valueOf(list.size()));
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
    private class RemoteDataTask0 extends AsyncTask<Void, Integer, List<AVIMConversation>> {

        public List<AVIMConversation> avimConversations = new ArrayList<AVIMConversation>();

        @Override
        protected List<AVIMConversation> doInBackground(Void... params) {

            List<String> list = new ArrayList<String>();
            list.add(AVUser.getCurrentUser().getUsername());

            final AVIMClient avimClient0 = AppData.getIMClient();
            final List<AVIMConversation> result = new ArrayList<AVIMConversation>();
            final List<String> queryClientIds = new ArrayList<String>();
            queryClientIds.addAll(list);

            AVIMConversationQuery query = avimClient0.getQuery();
            query.containsMembers(queryClientIds);
            query.findInBackground(new AVIMConversationQueryCallback() {
                @Override
                public void done(List<AVIMConversation> list, AVException e) {
                    if (null!=e){
                        Log.v("db.error4",e.getMessage());
                    }else {
                        avimConversations.addAll(list);
                        Log.v("db.cnm2", String.valueOf(list.size()));
                    }
                }
            });

            return avimConversations;
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
        protected void onPostExecute(List<AVIMConversation> result) {
            if (result!=null){
                Log.v("db.cnm0",String.valueOf(result.size()));
                recyclerView.setAdapter(new WallAdapter(getApplicationContext(),result));
            }else {
                Log.v("db.cnm1","null");
            }

        }
    }
}
