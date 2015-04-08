package com.example.db.civil;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;


import com.example.db.civil.fragment.BeautifulArticleFragment;
import com.example.db.civil.fragment.MyCollectionFragment;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;


public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener,MaterialSectionListener
                                                                        ,BeautifulArticleFragment.OnFragmentInteractionListener
                                                                        ,MyCollectionFragment.OnFragmentInteractionListener{


    @Override
    public void init(Bundle savedInstanceState) {
        // add accounts
        MaterialAccount account = new MaterialAccount(this.getResources(),"KG","846503548@qq.com", R.drawable.photo,R.drawable.db2);
        this.addAccount(account);

        // set listener
        this.setAccountListener(this);
        this.setAccountListener(this);


        this.addSection(newSection("建筑美文", R.drawable.ab_page_cam,new BeautifulArticleFragment()).setSectionColor(Color.parseColor("#24ba91")));
        this.addSection(newSection("我的收藏", R.drawable.ab_attach_audio,new MyCollectionFragment()).setSectionColor(Color.parseColor("#24ba91")));

        this.addBottomSection(newSection("设置",R.drawable.ic_settings_black_24dp,new Intent(this, MainActivity.class)));
    }

    @Override
    public void onAccountOpening(MaterialAccount account) {

    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
