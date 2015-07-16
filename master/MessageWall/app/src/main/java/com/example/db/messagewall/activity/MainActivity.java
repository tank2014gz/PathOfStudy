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

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.db.messagewall.fragment.AddMessageItemFragment;
import com.example.db.messagewall.fragment.AlertWallFragment;
import com.example.db.messagewall.fragment.AskMembersFragment;
import com.example.db.messagewall.fragment.CheeseListFragment;
import com.example.db.messagewall.fragment.MembersFragment;
import com.example.db.messagewall.fragment.MessageWallFeagment;
import com.example.db.messagewall.fragment.WallInfoFragment;
import com.example.db.messagewall.utils.AppConstant;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity implements MessageWallFeagment.OnFragmentInteractionListener
                                                                ,MembersFragment.OnFragmentInteractionListener
                                                                ,WallInfoFragment.OnFragmentInteractionListener
                                                                ,AskMembersFragment.OnFragmentInteractionListener
                                                                ,AlertWallFragment.OnFragmentInteractionListener
                                                                ,AddMessageItemFragment.OnFragmentInteractionListener{

    private DrawerLayout mDrawerLayout;

    public FragmentTransaction fragmentTransaction;

    public Toolbar toolbar;

    public static String CONVERSATION_ID;
    public Bundle bundle;

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

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MessageWallFeagment messageWallFeagment = new MessageWallFeagment();
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_home :

                        toolbar.setTitle(menuItem.getTitle());
                        MessageWallFeagment messageWallFeagment = new MessageWallFeagment();
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
                        fragmentTransaction.replace(R.id.container, new MessageWallFeagment()).commit();
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
                        AlertWallFragment alertWallFragment = new AlertWallFragment();
                        alertWallFragment.setArguments(bundle);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, alertWallFragment).commit();
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        break;

                    case R.id.nav_setting_feedback:

                        toolbar.setTitle(menuItem.getTitle());
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, new MessageWallFeagment()).commit();
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        break;

                }
                return true;
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
