package com.haitou.xiaoyoupai.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.base.TTBaseFragmentActivity;
import com.haitou.xiaoyoupai.ui.fragment.NextFragment;

public class NextActivity extends TTBaseFragmentActivity implements NextFragment.OnFragmentInteractionListener{

    public Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        bundle = this.getIntent().getExtras();

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        NextFragment nextFragment = new NextFragment();
        nextFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container,nextFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
