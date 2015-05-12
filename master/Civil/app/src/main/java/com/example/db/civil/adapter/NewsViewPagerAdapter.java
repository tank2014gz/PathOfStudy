package com.example.db.civil.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by db on 3/28/15.
 */
public class NewsViewPagerAdapter extends PagerAdapter {

    public Context context;

    public List<View> list;

    public NewsViewPagerAdapter(Context context, List<View> list){
        super();
        this.context=context;
        this.list=list;
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
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container,int position){
        container.addView(list.get(position),0);
        return list.get(position);
    }
}
