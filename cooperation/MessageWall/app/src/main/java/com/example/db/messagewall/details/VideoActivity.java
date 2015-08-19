package com.example.db.messagewall.details;

import android.animation.ArgbEvaluator;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.db.messagewall.activity.BaseActivity;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.indicators.observer.ViewPagerObserver;
import com.example.db.messagewall.view.indicators.tab.IconicProvider;
import com.example.db.messagewall.view.indicators.tab.IconicTabsView;
import com.example.db.messagewall.view.indicators.tab.effect.FadeIconicTabsEffect;
import com.example.db.messagewall.view.indicators.title.TitleIndicator;
import com.example.db.messagewall.view.indicators.title.transformer.DefaultTitleTransformer;
import com.example.db.messagewall.view.swipebacklayout.SwipeBackActivity;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends SwipeBackActivity implements VideoDetailsFragment.OnFragmentInteractionListener,
                                                           BaseCommentFragment.OnFragmentInteractionListener{

    private TitleIndicator titleIndicator;

    public List<Fragment> list;

    public Toolbar toolbar;

    public Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(null);
        if (Build.VERSION.SDK_INT >= 21)
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bundle = this.getIntent().getExtras();

        list = new ArrayList<Fragment>();
        VideoDetailsFragment videoDetailsFragment = new VideoDetailsFragment();
        videoDetailsFragment.setArguments(bundle);
        BaseCommentFragment baseCommentFragment = new BaseCommentFragment();
        baseCommentFragment.setArguments(bundle);
        list.add(videoDetailsFragment);
        list.add(baseCommentFragment);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),list));

        final ViewPagerObserver observer = new ViewPagerObserver(mViewPager);

        titleIndicator = (TitleIndicator) findViewById(R.id.titleIndicator);
        observer.addObservableView(titleIndicator);
        titleIndicator.setToolBar(toolbar);

        IconicTabsView iconicTabsView = (IconicTabsView) findViewById(R.id.iconicTabsView);
        iconicTabsView.setIconicTabsEffect(new FadeIconicTabsEffect());
        observer.addObservableView(iconicTabsView);

        ArgbEvaluator argbEvaluator;

        titleIndicator.setTitleTransformer(new DefaultTitleTransformer(VideoActivity.this));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private class MyAdapter extends FragmentPagerAdapter implements IconicProvider {


        int[] icons = {R.drawable.ic_home_white_24dp,R.drawable.ic_chat_bubble_white_24dp};

        String[] titles = {"详情","评论"};

        public List<Fragment> list;

        public MyAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getIconicDrawable(int position) {
            return icons[position];
        }
    }
    public Toolbar getToolbar(){
        return toolbar;
    }
}
