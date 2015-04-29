package com.example.db.tline;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.db.tline.fragment.FabPictureFragment;
import com.example.db.tline.fragment.FabTextFragment;
import com.example.db.tline.fragment.HomeFragment;
import com.example.db.tline.fragment.PersonalFragment;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;


public class MainActivity extends ActionBarActivity implements FabTextFragment.OnFragmentInteractionListener,HomeFragment.OnFragmentInteractionListener
                                                                ,FabPictureFragment.OnFragmentInteractionListener
                                                                ,PersonalFragment.OnFragmentInteractionListener
                                                                {
    public FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentTransaction=this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.activity_up_move_in,R.anim.abc_fade_out);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new HomeFragment())
                    .commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2&&resultCode== Activity.RESULT_OK){
            Uri uri=data.getData();
            Log.v("haha",uri.toString());

            FabPictureFragment fabPictureFragment=new FabPictureFragment();
            Bundle bundle=new Bundle();
            bundle.putString("uri",uri.toString());
            fabPictureFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.container,fabPictureFragment).commit();


        }
    }
}
