/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.db.messagewall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.db.messagewall.adapter.ColorPickerAdapter;
import com.example.db.messagewall.fragment.AddMessageItemFragment;
import com.example.db.messagewall.fragment.AlertWallFragment;
import com.example.db.messagewall.fragment.AskMembersFragment;
import com.example.db.messagewall.fragment.EditWallPaperFragment;
import com.example.db.messagewall.fragment.MembersFragment;
import com.example.db.messagewall.fragment.MessageWallFragment;
import com.example.db.messagewall.fragment.WallInfoFragment;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.utils.ThemeHelper;
import com.example.db.messagewall.view.CircleImageView;
import com.example.db.messagewall.view.MaterialDialog;
import com.support.android.designlibdemo.R;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;

/**
 * TODO
 */
public class MainActivity extends BaseActivity implements MessageWallFragment.OnFragmentInteractionListener
                                                                ,MembersFragment.OnFragmentInteractionListener
                                                                ,WallInfoFragment.OnFragmentInteractionListener
                                                                ,AskMembersFragment.OnFragmentInteractionListener
                                                                ,AlertWallFragment.OnFragmentInteractionListener
                                                                ,AddMessageItemFragment.OnFragmentInteractionListener
                                                                ,EditWallPaperFragment.OnFragmentInteractionListener{

    private DrawerLayout mDrawerLayout;

    public FragmentTransaction fragmentTransaction;

    public Toolbar toolbar;

    public static String CONVERSATION_ID;
    public Bundle bundle;

    /*
    友盟的反馈
     */
    FeedbackAgent fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        /*
        获取群组的唯一ID,此后可以通过这个ID来寻找群组
         */
        bundle = this.getIntent().getExtras();
        CONVERSATION_ID = bundle.getString("_ID");
        Log.v("db.test0",CONVERSATION_ID);

        fb = new FeedbackAgent(this);
        fb.sync();
        fb.openFeedbackPush();
        PushAgent.getInstance(this).enable();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MessageWallFragment messageWallFeagment = new MessageWallFragment();
        messageWallFeagment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, messageWallFeagment ).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_about:
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                break;
            case R.id.action_feedback:
                Intent intent = new Intent(MainActivity.this,CustomActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settheme:
                final MaterialDialog materialDialog = new MaterialDialog(MainActivity.this);
                View view = LayoutInflater.from(this).inflate(R.layout.color_picker,null);
                ListView listView = (ListView)view.findViewById(R.id.listview);
                final ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(MainActivity.this);
                listView.setAdapter(colorPickerAdapter);
                materialDialog.setView(view);
                materialDialog.setCanceledOnTouchOutside(true);
                materialDialog.setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        materialDialog.dismiss();
                    }
                }).setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (colorPickerAdapter.Flag){
                        ThemeHelper.setTheme(MainActivity.this, colorPickerAdapter.getThemeId());
                        reload();
                            materialDialog.dismiss();
                        }else {
                            materialDialog.dismiss();
                        }
                    }
                });
                materialDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {

        CircleImageView imageView = (CircleImageView)navigationView.findViewById(R.id.account_logo);
        TextView textView = (TextView)navigationView.findViewById(R.id.account_name);
        textView.setText("ID: "+AVUser.getCurrentUser().getUsername());
        /*
        显示用户设置的logo
         */
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.db.alife_walllogo", Context.MODE_PRIVATE);
        String paper = sharedPreferences.getString("paper_path","");
        if(paper.equals("")){
            imageView.setBackgroundResource(R.drawable.ic_launcher);
        }else {
            Bitmap bitmap = BitmapFactory.decodeFile(paper);
            imageView.setImageBitmap(bitmap);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_home :

                        toolbar.setTitle(menuItem.getTitle());
                        MessageWallFragment messageWallFeagment = new MessageWallFragment();
                        messageWallFeagment.setArguments(bundle);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container,messageWallFeagment ).commit();
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        break;

                    case R.id.nav_partner:

                        toolbar.setTitle(menuItem.getTitle());
                        MembersFragment membersFragment = new MembersFragment();
                        membersFragment.setArguments(bundle);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, membersFragment).commit();
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        break;

                    case R.id.nav_messages:

                        toolbar.setTitle(menuItem.getTitle());
                        WallInfoFragment wallInfoFragment = new WallInfoFragment();
                        wallInfoFragment.setArguments(bundle);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, wallInfoFragment).commit();
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        break;

                    case R.id.nav_edit:

                        toolbar.setTitle(menuItem.getTitle());
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        EditWallPaperFragment editWallPaperFeagment = new EditWallPaperFragment();
                        editWallPaperFeagment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.container, editWallPaperFeagment).commit();
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        break;

                    case R.id.nav_add:

                        toolbar.setTitle(menuItem.getTitle());
                        AskMembersFragment askMembersFragment = new AskMembersFragment();
                        askMembersFragment.setArguments(bundle);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, askMembersFragment).commit();
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        break;

                    case R.id.nav_alert:

                        toolbar.setTitle(menuItem.getTitle());
                        startActivity(new Intent(MainActivity.this,AlertWallActivity.class));
                        MainActivity.this.finish();
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        break;

                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       if (requestCode==2&&resultCode== Activity.RESULT_OK){
           if (data!=null){
               Uri uri = data.getData();
               String path = AppConstant.getPath(MainActivity.this,uri);
               SharedPreferences sharedPreferences = getApplicationContext()
                       .getSharedPreferences("com.example.db.alife_wallpaper"
                               , Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString("paper_path", path);
               editor.commit();
               Log.v("paper_path",path);
           }else {
               AppConstant.showSelfToast(MainActivity.this,"选择失败！");
           }
       }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
