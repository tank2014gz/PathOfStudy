package com.example.db.locationhelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.db.locationhelper.activity.MapActivity;
import com.example.db.locationhelper.fragment.DeviceFragment;
import com.example.db.locationhelper.fragment.HistoryFragment;
import com.example.db.locationhelper.fragment.NoticeFragment;
import com.example.db.locationhelper.fragment.HomeFragment;
import com.example.db.locationhelper.fragment.SearchFragment;
import com.example.db.locationhelper.utils.AppConstant;
import com.example.db.locationhelper.view.CircleImageView;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener,
                                                                HomeFragment.OnFragmentInteractionListener,
                                                                NoticeFragment.OnFragmentInteractionListener,
                                                                DeviceFragment.OnFragmentInteractionListener,
                                                                HistoryFragment.OnFragmentInteractionListener{

    private DrawerLayout mDrawerLayout;

    public FragmentTransaction fragmentTransaction;

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true,this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.container,homeFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_map) {

            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        CircleImageView imageView = (CircleImageView)navigationView.findViewById(R.id.account_logo);
        final TextView textView = (TextView)navigationView.findViewById(R.id.account_name);
        final TextView nichen = (TextView)navigationView.findViewById(R.id.account_nichen);
        /*
        显示昵称
         */
//        AVQuery<AVObject> query = new AVQuery<AVObject>("NiChen");
//        query.whereEqualTo("username", AVUser.getCurrentUser().getUsername());
//        query.findInBackground(new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> list, AVException e) {
//                if (e==null){
//                    AVObject avObject = (AVObject)list.get(0);
//                    if (avObject.get("nichen").toString()!=null){
//                        textView.setText("账号: "+AVUser.getCurrentUser().getUsername());
//                        nichen.setText("昵称: "+avObject.get("nichen").toString());
//                    }else {
//                        textView.setText("账号: "+AVUser.getCurrentUser().getUsername());
//                        nichen.setText("昵称: "+AVUser.getCurrentUser().getUsername());
//                    }
//                }else {
//                    e.printStackTrace();
//                }
//            }
//        });
        /*
        显示用户设置的logo
         */
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.db.alife_walllogo", Context.MODE_PRIVATE);
        String paper = sharedPreferences.getString("paper_path","");
        if(paper.equals("")){
            imageView.setBackgroundResource(R.drawable.head_xiaoqiang_m);
        }else {
            Bitmap bitmap = BitmapFactory.decodeFile(paper);
            imageView.setImageBitmap(bitmap);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        FragmentTransaction fragmentTransaction;
                        switch (menuItem.getItemId()){

                            case R.id.nav_home :

                                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                HomeFragment homeFragment = new HomeFragment();
                                fragmentTransaction.replace(R.id.container,homeFragment).commit();

                                toolbar.setTitle(menuItem.getTitle());
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawers();

                                break;

                            case R.id.nav_device:

                                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                DeviceFragment deviceFragment = new DeviceFragment();
                                fragmentTransaction.replace(R.id.container, deviceFragment).commit();

                                toolbar.setTitle(menuItem.getTitle());
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawers();

                                break;

                            case R.id.nav_notice:

                                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                NoticeFragment noticeFragment = new NoticeFragment();
                                fragmentTransaction.replace(R.id.container, noticeFragment).commit();

                                toolbar.setTitle(menuItem.getTitle());
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawers();

                                break;
                            case R.id.nav_history:

                                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                HistoryFragment historyFragment = new HistoryFragment();
                                fragmentTransaction.replace(R.id.container, historyFragment).commit();

                                toolbar.setTitle(menuItem.getTitle());
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawers();

                                break;
                            case R.id.nav_setting:

                                toolbar.setTitle(menuItem.getTitle());
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
