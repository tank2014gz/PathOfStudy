package com.haitou.xiaoyoupai.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.activity.ActivityDetailsActivity;

/**
 * Created by db on 8/27/15.
 */
public class ActivityListAdapter extends BaseAdapter {

    public Context context;

    public ActivityListAdapter(Context context){
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.img);
            viewHolder.tv_name = (TextView)view.findViewById(R.id.activity_name);
            viewHolder.tv_time = (TextView)view.findViewById(R.id.activity_time);
            viewHolder.tv_date = (TextView)view.findViewById(R.id.activity_date);
            viewHolder.tv_position = (TextView)view.findViewById(R.id.activity_position);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return view;
    }

    public static class ViewHolder{
        public ImageView imageView;
        public TextView tv_name,tv_time,tv_date,tv_position;
    }
}
