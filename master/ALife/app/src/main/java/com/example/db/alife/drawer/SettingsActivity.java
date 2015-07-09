/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.db.alife.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.db.alife.R;
import com.example.db.alife.activity.AboutActivity;
import com.example.db.alife.activity.FeedBackActivity;
import com.example.db.alife.utils.AppConstant;
import com.example.db.alife.view.ALifeToast;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    public Toolbar toolbar;

    public LinearLayout mAbout,mFeedBack;
    public CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initToolBar();

        mAbout = (LinearLayout)findViewById(R.id.about);
        mFeedBack = (LinearLayout)findViewById(R.id.feedback);
        mCheckBox = (CheckBox)findViewById(R.id.isCheckUpdate);

        mAbout.setOnClickListener(this);
        mFeedBack.setOnClickListener(this);

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ALifeToast.makeText(SettingsActivity.this,"自动检查更新！", ALifeToast.ToastType.SUCCESS,ALifeToast.LENGTH_SHORT).show();
                }else {
                    ALifeToast.makeText(SettingsActivity.this,"不自动检查更新！", ALifeToast.ToastType.SUCCESS,ALifeToast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about:
                Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.feedback:
                Intent intent0 = new Intent(SettingsActivity.this, FeedBackActivity.class);
                startActivity(intent0);
                break;
        }
    }
}
