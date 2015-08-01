package com.example.db.messagewall.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.db.messagewall.adapter.WallAdapter;
import com.example.db.messagewall.adapter.WallSwipeAdapter;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.scanner.MipcaActivityCapture;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.utils.ThemeHelper;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.MaterialDialog;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends BaseActivity {

    public static final String EXTRA_NAME = "cheese_name";

    public FloatingActionButton floatingActionButton;

    public SwipeRefreshLayout mSwipeRefreshLayout;
    public RecyclerView recyclerView;

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
                if (AVUser.getCurrentUser()!=null){
                    List<String> list = new ArrayList<String>();
                    list.add(AVUser.getCurrentUser().getUsername());

                    AppData.setClientIdToPre(AVUser.getCurrentUser().getUsername());

                    final AVIMClient avimClient0 = AppData.getIMClient();
                    final List<String> queryClientIds = new ArrayList<String>();
                    queryClientIds.addAll(list);

                    avimClient0.open(new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient avimClient, AVException e) {
                            if (e==null){
                                AVIMConversationQuery query = avimClient.getQuery();
                                query.containsMembers(queryClientIds);
                                query.findInBackground(new AVIMConversationQueryCallback() {
                                    @Override
                                    public void done(List<AVIMConversation> list, AVException e) {
                                        if (null != e) {
                                            Log.v("db.error4", e.getMessage());
                                        } else {
                                            recyclerView.setAdapter(new WallAdapter(SelectActivity.this, list));
                                            Log.v("db.cnm2", String.valueOf(list.size()));
                                        }
                                    }
                                });
                            }else {
                                Log.v("db.error11",e.getMessage());
                            }
                        }
                    });
                }else {
                    AppConstant.showSelfToast(getApplicationContext(),"请先登陆或注册！");
                }
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

                    final MaterialDialog materialDialog = new MaterialDialog(SelectActivity.this);
                    View view = LayoutInflater.from(getApplicationContext())
                            .inflate(R.layout.select_dialog,null);
                    TextView scanner = (TextView)view.findViewById(R.id.scanner);
                    TextView add = (TextView)view.findViewById(R.id.add);
                    scanner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SelectActivity.this,MipcaActivityCapture.class);
                            startActivity(intent);
                            materialDialog.dismiss();
                        }
                    });
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SelectActivity.this,AddMessageWallActivity.class);
                            startActivity(intent);
                            materialDialog.dismiss();
                        }
                    });
                    materialDialog.setView(view)
                                  .setCanceledOnTouchOutside(true);
                    materialDialog.show();


                }else {
                    Intent intent = new Intent(SelectActivity.this,SignUpActivity.class);
                    startActivity(intent);
                }
            }
        });

        /*
        刷新加载
         */
        if (AVUser.getCurrentUser()!=null){
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
                                    recyclerView.setAdapter(new WallAdapter(SelectActivity.this, list));
                                    Log.v("db.cnm2", String.valueOf(list.size()));
                                }
                            }
                        });
                    } else {
                        Log.v("db.error11", e.getMessage());
                    }
                }
            });
        }else {
            AppConstant.showSelfToast(getApplicationContext(),"请先登陆或注册！");
        }

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
                recyclerView.setAdapter(new WallAdapter(getApplicationContext(), result));
            }else {
                Log.v("db.cnm1","null");
            }

        }
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
