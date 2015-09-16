package com.haitou.xiaoyoupai.ui.activity;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.base.TTBaseActivity;
import com.haitou.xiaoyoupai.ui.base.TTBaseFragmentActivity;
import com.haitou.xiaoyoupai.ui.fragment.NNextFragment;
import com.haitou.xiaoyoupai.ui.fragment.NextFragment;

public class NNextActivity extends TTBaseFragmentActivity implements NNextFragment.OnFragmentInteractionListener{

    public Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nnext);


        bundle = this.getIntent().getExtras();

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        NNextFragment nextFragment = new NNextFragment();
        nextFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container,nextFragment).commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
