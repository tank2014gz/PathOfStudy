package com.example.db.messagewall.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.db.messagewall.adapter.AboutAdapter;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.swipebacklayout.SwipeBackActivity;
import com.example.db.messagewall.view.swipebacklayout.SwipeBackLayout;
import com.support.android.designlibdemo.R;

public class AboutActivity extends SwipeBackActivity {

    public Toolbar toolbar;

    public ListView mListView;

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initToolBar();

        mListView = (ListView)findViewById(R.id.listview);

        AboutAdapter aboutAdapter = new AboutAdapter(getApplicationContext());
        mListView.setAdapter(aboutAdapter);

        mSwipeBackLayout = getSwipeBackLayout();

    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("关于");
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));

        if (Build.VERSION.SDK_INT >= 21)
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
