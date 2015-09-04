package com.haitou.xiaoyoupai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.widget.CircleImageView;

/**
 * Created by db on 8/29/15.
 */
public class CommentAdapter extends BaseAdapter{

    public Context context;

    public CommentAdapter(Context context){
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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
            view = LayoutInflater.from(context).inflate(R.layout.comment_item,null);
            viewHolder = new ViewHolder();
            viewHolder.from = (TextView)view.findViewById(R.id.from);
            viewHolder.to = (TextView)view.findViewById(R.id.to);
            viewHolder.date = (TextView)view.findViewById(R.id.date);
            viewHolder.content = (TextView)view.findViewById(R.id.comment);
            viewHolder.circleImageView = (CircleImageView)view.findViewById(R.id.img);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        return view;
    }

    public static class ViewHolder{
        TextView from,to,date,content;
        CircleImageView circleImageView;
    }
}
