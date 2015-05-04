package com.example.db.tline.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.db.tline.R;
import com.example.db.tline.adapter.HomeListAdapter;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.PullToZoomListView;
import com.example.db.tline.view.RevealLayout;

public class HomePagerActicity extends ActionBarActivity {

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;


    public PullToZoomListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pager_acticity);

        mListView=(PullToZoomListView)findViewById(R.id.list_view);
        mListView.getHeaderView().setImageResource(R.drawable.scroll2);
        mListView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);


        mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
        mRevealLayout.setContentShown(false);
        mRevealLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRevealLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mRevealLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRevealLayout.show();
                    }
                }, 50);
            }
        });

        HomeListAdapter homeListAdapter=new HomeListAdapter(getApplicationContext());
        mListView.setAdapter(homeListAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_pager_acticity, menu);
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
