package com.example.db.tline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db.tline.R;

import java.util.ArrayList;

/**
 * Created by db on 4/30/15.
 */
public class HomeListAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<View> mList;

    public HomeListAdapter(Context context){
        super();
        this.context=context;

        mList=new ArrayList<View>();

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder viewHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.home_viewpager,null);
            viewHolder=new ViewHolder();
            viewHolder.temp=(TextView)view.findViewById(R.id.temp);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();

        }



        return view;
    }
    public static class ViewHolder{
        TextView temp;
    }
}
