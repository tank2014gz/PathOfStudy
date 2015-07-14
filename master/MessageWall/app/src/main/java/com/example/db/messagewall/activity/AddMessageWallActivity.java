package com.example.db.messagewall.activity;

import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.support.android.designlibdemo.R;

public class AddMessageWallActivity extends AppCompatActivity {

    public Toolbar toolbar;

    public MaterialEditText mWallName,mNiChen,mWallDescription;
    public MaterialEditText mFriends;
    public LinearLayout mAsk,mRemove;
    public LinearLayout mAdd;
    public FloatingActionButton mPutForward;

    public String wall_name,wall_nichen,wall_description,friends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message_wall);

        initToolBar();

        mWallName = (MaterialEditText)findViewById(R.id.edit_wall_name);
        mNiChen = (MaterialEditText)findViewById(R.id.edit_wall_nichen);
        mWallDescription = (MaterialEditText)findViewById(R.id.edit_wall_description);
        mFriends = (MaterialEditText)findViewById(R.id.edit_wall_friend);
        mAsk = (LinearLayout)findViewById(R.id.btn_add);
        mRemove = (LinearLayout)findViewById(R.id.btn_remove);
        mAdd = (LinearLayout)findViewById(R.id.ask);
        mPutForward = (FloatingActionButton)findViewById(R.id.fab);

        mAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdd.addView(createView());
            }
        });

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdd.removeAllViews();
            }
        });


    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加一面墙");
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
        getMenuInflater().inflate(R.menu.menu_add_message_wall, menu);
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
    public View createView(){

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.add_item, null);
        view.setLayoutParams(layoutParams);

        return view;
    }
}
