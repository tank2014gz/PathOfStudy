package com.example.db.slor.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.db.slor.MainActivity;
import com.example.db.slor.R;
import com.example.db.slor.fragment.BeautifulArticleFragment;
import com.example.db.slor.fragment.BeautifulPictureFragment;
import com.example.db.slor.fragment.MyCollectionFragment;
import com.example.db.slor.fragment.SmileVideoFrament;
import com.example.db.slor.fragment.WarmHeartFragment;
import com.example.db.slor.utils.Utils;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;
/**
 * Created by db on 3/23/15.
 */
public class NavigatorDrawerActivity extends MaterialNavigationDrawer implements MaterialAccountListener,MaterialSectionListener
                                                                                ,BeautifulArticleFragment.OnFragmentInteractionListener
                                                                                ,BeautifulPictureFragment.OnFragmentInteractionListener
                                                                                ,WarmHeartFragment.OnFragmentInteractionListener
                                                                                ,SmileVideoFrament.OnFragmentInteractionListener
                                                                                ,MyCollectionFragment.OnFragmentInteractionListener{
    @Override
    public void init(Bundle savedInstanceState) {
        if (Utils.USER_OBJECTED==null){
            Utils.USER_OBJECTED=this.getIntent().getStringExtra("objected");

        }


        // add accounts
        MaterialAccount account = new MaterialAccount(this.getResources(),"KG","846503548@qq.com", R.drawable.photo,R.drawable.db2);
        this.addAccount(account);

        MaterialAccount account2 = new MaterialAccount(this.getResources(),"KK","3025673709@qq.com",R.drawable.photo2,R.drawable.db2);
        this.addAccount(account2);


        // set listener
        this.setAccountListener(this);

        // set listener
        this.setAccountListener(this);

        // create sections
        this.addSection(newSection("心灵美文", R.drawable.ab_page_cam,new BeautifulArticleFragment()).setSectionColor(Color.parseColor("#24ba91")));
        this.addSection(newSection("唯美图片", R.drawable.ab_attach_picture,new BeautifulPictureFragment()).setSectionColor(Color.parseColor("#24ba91")));
        this.addSection(newSection("暖心歌曲", R.drawable.ab_audio,new WarmHeartFragment()).setSectionColor(Color.parseColor("#24ba91")));
        this.addSection(newSection("搞笑视频", R.drawable.ab_video,new SmileVideoFrament()).setSectionColor(Color.parseColor("#24ba91")));
        this.addSection(newSection("我的收藏", R.drawable.ab_attach_audio,new MyCollectionFragment()).setSectionColor(Color.parseColor("#24ba91")));

        // create bottom section
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
