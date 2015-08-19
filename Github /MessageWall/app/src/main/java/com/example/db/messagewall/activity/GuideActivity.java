package com.example.db.messagewall.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.db.messagewall.utils.AppConstant;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {


    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus0(true,this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        button = (Button)findViewById(R.id.send);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this,SelectActivity.class);
                startActivity(intent);
                GuideActivity.this.finish();
            }
        });
    }
}
