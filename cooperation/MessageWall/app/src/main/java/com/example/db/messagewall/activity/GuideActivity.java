package com.example.db.messagewall.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.db.messagewall.utils.AppConstant;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    public ViewPager viewPager;

    public List<View> list;
    public List<LinearLayout> linearLayouts;
    public LinearLayout file,mute,picture,text;

    public Handler handler;

    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus0(true,this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager)findViewById(R.id.viewpager);

        list = new ArrayList<View>();
        linearLayouts = new ArrayList<LinearLayout>();

        View viewOne = LayoutInflater.from(GuideActivity.this).inflate(R.layout.guide_one,null);
        View viewTwo = LayoutInflater.from(GuideActivity.this).inflate(R.layout.guide_two,null);
        file = (LinearLayout)viewTwo.findViewById(R.id.file);
        mute = (LinearLayout)viewTwo.findViewById(R.id.mute);
        picture = (LinearLayout)viewTwo.findViewById(R.id.picture);
        text = (LinearLayout)viewTwo.findViewById(R.id.text);
        linearLayouts.add(file);
        linearLayouts.add(mute);
        linearLayouts.add(picture);
        linearLayouts.add(text);
        View viewThree = LayoutInflater.from(GuideActivity.this).inflate(R.layout.guide_three,null);
        button = (Button)viewThree.findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(GuideActivity.this,SelectActivity.class);
                startActivity(intent);
                GuideActivity.this.finish();
            }
        });

        list.add(viewOne);
        list.add(viewTwo);
        list.add(viewThree);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(list);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showAnimation(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter{

        public List<View> list;

        public ViewPagerAdapter(List<View> list){
            super();
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {
            container.removeView(list.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(list.get(position), 0);//添加页卡
            return list.get(position);
        }

    }

    public void showAnimation(int position){
        switch (position){
            case 0:

                break;
            case 1:
                handler = new Handler();
                int delay = 400;
                for (final LinearLayout linearLayout:linearLayouts){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            linearLayout.setVisibility(ViewGroup.VISIBLE);
                        }
                    }, delay);
                    delay += 150;
                }

                break;
            case 2:

                break;
        }
    }
}
