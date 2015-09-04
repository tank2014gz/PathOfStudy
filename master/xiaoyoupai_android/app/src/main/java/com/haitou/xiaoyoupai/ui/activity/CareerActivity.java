package com.haitou.xiaoyoupai.ui.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.base.TTBaseFragmentActivity;
import com.haitou.xiaoyoupai.ui.fragment.CareerFragment;

public class CareerActivity extends TTBaseFragmentActivity implements CareerFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
