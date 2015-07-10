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
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


import com.example.db.alife.R;
import com.example.db.alife.activity.SignUpActivity;
import com.example.db.alife.fragment.ArtLife;
import com.example.db.alife.fragment.BeautifulSentence;
import com.example.db.alife.fragment.Collection;
import com.example.db.alife.fragment.CollectionMoto;
import com.example.db.alife.fragment.CollectionRead;
import com.example.db.alife.fragment.CollectionSentence;
import com.example.db.alife.fragment.EnglishMoto;
import com.example.db.alife.fragment.EnglishRead;
import com.example.db.alife.fragment.ShootWorld;
import com.example.db.alife.liveo.interfaces.NavigationLiveoListener;
import com.example.db.alife.liveo.navigationliveo.NavigationLiveo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends NavigationLiveo implements NavigationLiveoListener
                                                                                    ,EnglishMoto.OnFragmentInteractionListener
                                                                                    ,EnglishRead.OnFragmentInteractionListener
                                                                                    ,BeautifulSentence.OnFragmentInteractionListener
                                                                                    ,ShootWorld.OnFragmentInteractionListener
                                                                                    ,ArtLife.OnFragmentInteractionListener
                                                                                    ,com.example.db.alife.fragment.Collection.OnFragmentInteractionListener
                                                                                    ,CollectionMoto.OnFragmentInteractionListener
                                                                                    ,CollectionRead.OnFragmentInteractionListener
                                                                                    ,CollectionSentence.OnFragmentInteractionListener{

    public List<String> mListNameItem;

    @Override
    public void onUserInformation() {
        //User information here
        this.mUserName.setText("db");
        this.mUserEmail.setText("3025673709@qq.com");
        this.mUserPhoto.setImageResource(R.drawable.ic_rudsonlive);
        this.mUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.uniBoys.Xiaoshiji", Context.MODE_PRIVATE);
        this.mUserName.setText(sharedPreferences.getString("NAME","db"));

    }

    @Override
    public void onInt(Bundle savedInstanceState) {
        //Creation of the list items is here


        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        UmengUpdateAgent.setUpdateCheckConfig(true);

        PushAgent mPushAgent = PushAgent.getInstance(getApplicationContext());
        mPushAgent.enable();

        PushAgent.getInstance(getApplicationContext()).onAppStart();

        // set listener {required}
        this.setNavigationListener(this);
        if (savedInstanceState == null) {
            //First item of the position selected from the list
            this.setDefaultStartPositionNavigation(1);
        }

        // name of the list items
        mListNameItem = new ArrayList<>();
        mListNameItem.add(0, "所有");
        mListNameItem.add(1,"英文名言");
        mListNameItem.add(2,"英文阅读");
        mListNameItem.add(3,"唯美句子");
//        mListNameItem.add(3, getString(R.string.drafts));
        mListNameItem.add(4,"摄影世界");
        mListNameItem.add(5,"艺术人生");
        mListNameItem.add(6,"我的收藏");


        // icons list items
        List<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, 0);
        mListIconItem.add(1, R.drawable.ic_create_white_24dp); //Item no icon set 0
        mListIconItem.add(2, R.drawable.ic_drafts_white_24dp); //Item no icon set 0
        mListIconItem.add(3, R.drawable.ic_filter_list_white_24dp); //When the item is a subHeader the value of the icon 0
        mListIconItem.add(4, R.drawable.ic_camera_alt_white_24dp);
        mListIconItem.add(5, R.drawable.ic_link_white_24dp);
        mListIconItem.add(6, R.drawable.ic_camera_white_24dp);

        //{optional} - Among the names there is some subheader, you must indicate it here
        List<Integer> mListHeaderItem = new ArrayList<>();

        //{optional} - Among the names there is any item counter, you must indicate it (position) and the value here
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        mSparseCounterItem.put(0, 0);
        mSparseCounterItem.put(1, 0);
        mSparseCounterItem.put(2, 0);
        mSparseCounterItem.put(5, 0);

        //If not please use the FooterDrawer use the setFooterVisible(boolean visible) method with value false
        this.setFooterInformationDrawer(R.string.settings, R.drawable.btn_settings);

        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);
    }

    @Override
    public void onItemClickNavigation(int position, int layoutContainerId) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment = null;
        switch (position){
            case 0:

                break;
            case 1:
                mFragment = new EnglishMoto().newInstance(mListNameItem.get(position));
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                }
                break;
            case 2:
                mFragment = new EnglishRead().newInstance(mListNameItem.get(position));
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                }
                break;
            case 3:
                mFragment = new BeautifulSentence().newInstance(mListNameItem.get(position));
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                }
                break;
            case 4:
                mFragment = new ShootWorld().newInstance(mListNameItem.get(position));
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                }
                break;
            case 5:
                mFragment = new ArtLife().newInstance(mListNameItem.get(position));
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                }
                break;
            case 6:
                mFragment = new Collection().newInstance(mListNameItem.get(position));
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
                }
                break;

        }

    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int position, boolean visible) {

        //hide the menu when the navigation is opens
//        menu.findItem(R.id.menu_search).setVisible(!visible);

    }

    @Override
    public void onClickUserPhotoNavigation(View v) {
        //user photo onClick
        Toast.makeText(this, R.string.open_user_profile, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickFooterItemNavigation(View v) {
        //footer onClick
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
