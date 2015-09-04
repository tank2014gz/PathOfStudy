package com.haitou.xiaoyoupai.ui.activity;

import android.net.Uri;
import android.os.Bundle;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.base.TTBaseFragmentActivity;
import com.haitou.xiaoyoupai.ui.fragment.NextFragment;

public class NextActivity extends TTBaseFragmentActivity implements NextFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
