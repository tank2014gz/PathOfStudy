package com.example.db.civil;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;


import com.example.db.civil.activity.LoginActivity;
import com.example.db.civil.activity.SubscribeActivity;
import com.example.db.civil.fragment.BeautifulArticleFragment;
import com.example.db.civil.fragment.FormatFragment;
import com.example.db.civil.fragment.NewsFragment;
import com.example.db.civil.fragment.RuleFragment;
import com.example.db.civil.fragment.SoftWareFragment;
import com.example.db.civil.utlis.AppConstant;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;


public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener,MaterialSectionListener
                                                                        ,BeautifulArticleFragment.OnFragmentInteractionListener
                                                                        ,RuleFragment.OnFragmentInteractionListener
                                                                        ,SoftWareFragment.OnFragmentInteractionListener
                                                                        ,NewsFragment.OnFragmentInteractionListener
                                                                        ,FormatFragment.OnFragmentInteractionListener{


    @Override
    public void init(Bundle savedInstanceState) {
        // add accounts
        MaterialAccount account = new MaterialAccount(this.getResources(), AppConstant.NICHEN,AppConstant.USER_NAME, R.drawable.photo,R.drawable.db2);
        this.addAccount(account);

        // set listener
        this.setAccountListener(this);

        this.addSection(newSection("建筑资讯", R.drawable.ab_attach_audio,new NewsFragment()).setSectionColor(Color.parseColor("#24ba91")));
        this.addSection(newSection("建筑美文", R.drawable.ab_page_cam,new BeautifulArticleFragment()).setSectionColor(Color.parseColor("#24ba91")));
        this.addSection(newSection("建筑论坛",R.drawable.ab_dictate,new FormatFragment()).setSectionColor(Color.parseColor("#24ba91")));
        this.addSection(newSection("建筑规范",R.drawable.ab_attach_picture,new RuleFragment()).setSectionColor(Color.parseColor("#24ba91")));
        this.addSection(newSection("建筑软件",R.drawable.ab_attach_video,new SoftWareFragment()).setSectionColor(Color.parseColor("#24ba91")));

        this.addBottomSection(newSection("设置",R.drawable.ic_settings_black_24dp,new Intent(this, SubscribeActivity.class)));
    }

    @Override
    public void onAccountOpening(MaterialAccount account) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
