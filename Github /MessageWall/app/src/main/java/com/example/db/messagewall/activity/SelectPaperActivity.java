package com.example.db.messagewall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.db.messagewall.adapter.ItemPaperAdapter;
import com.example.db.messagewall.adapter.WallPaperGridAdapter;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.fab.FloatingActionMenu;
import com.example.db.messagewall.view.swipebacklayout.SwipeBackActivity;
import com.support.android.designlibdemo.R;

public class SelectPaperActivity extends SwipeBackActivity {

    public Toolbar toolbar;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    public GridView mGridView;
    public ItemPaperAdapter wallPaperGridAdapter;
    public com.example.db.messagewall.view.fab.FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_paper);

        initToolBar();

        bundle = this.getIntent().getExtras();
        CONVERSATION_ID = bundle.getString("_ID");

        floatingActionButton = (com.example.db.messagewall.view.fab.FloatingActionButton)findViewById(R.id.add);
        floatingActionButton.show(true);

        mGridView = (GridView)findViewById(R.id.gridview);

        wallPaperGridAdapter = new ItemPaperAdapter(SelectPaperActivity.this);
        mGridView.setAdapter(wallPaperGridAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wallPaperGridAdapter.flag){

                    SharedPreferences sharedPreferences = SelectPaperActivity.this
                            .getSharedPreferences("com.example.db.alife_wallitempaper"
                                    , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("paper_path", wallPaperGridAdapter.getPath());
                    editor.commit();

                    Intent intent = new Intent(SelectPaperActivity.this, AddMessageItemActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("_ID",CONVERSATION_ID);
                    intent.putExtras(bundle);
                    SelectPaperActivity.this.finish();
                    startActivity(intent);
                }else {
                    ALifeToast.makeText(SelectPaperActivity.this
                            , "请选择图片！"
                            , ALifeToast.ToastType.SUCCESS
                            , ALifeToast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("选择留言纸");
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

}
