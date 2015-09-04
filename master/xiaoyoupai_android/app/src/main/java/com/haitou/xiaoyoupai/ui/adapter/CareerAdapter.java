package com.haitou.xiaoyoupai.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.haitou.xiaoyoupai.R;

/**
 * Created by db on 9/4/15.
 */
public class CareerAdapter extends BaseAdapter {

    public Context context;

    public CareerAdapter(Context context){
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

        final ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.career_item,null);
            viewHolder = new ViewHolder();
            viewHolder.content = (TextView)view.findViewById(R.id.content);
            viewHolder.checkBox = (CheckBox)view.findViewById(R.id.Notice_signup_Checkbox);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.checkBox.setVisibility(View.GONE);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                viewHolder.checkBox.setVisibility(View.VISIBLE);

                return false;
            }
        });

        return view;
    }

    public static class ViewHolder{
        TextView content;
        CheckBox checkBox;
    }
}
