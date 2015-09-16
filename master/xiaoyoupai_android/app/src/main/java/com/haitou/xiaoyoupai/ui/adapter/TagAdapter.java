package com.haitou.xiaoyoupai.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haitou.xiaoyoupai.R;

/**
 * Created by db on 9/10/15.
 */
public class TagAdapter extends BaseAdapter{

    public String[] tags = new String[]{"姓名","手机","学校","学院","单位","职位","行业","+"};

    public Context context;

    public boolean flag = false;

    public TagAdapter(Context context){
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return tags.length;
    }

    @Override
    public Object getItem(int position) {
        return tags[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.tag_item,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView)convertView.findViewById(R.id.type);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (position==0||position==1||position==2){

            viewHolder.textView.setBackgroundColor(context.getResources().getColor(R.color.default_blue_color));
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.default_bk));

        }else {
            viewHolder.textView.setBackground(context.getResources().getDrawable(R.drawable.edit_bkg));
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.default_light_grey_color));
        }

        viewHolder.textView.setText(tags[position]);


        return convertView;
    }

    public static class ViewHolder{
        TextView textView;
    }
}
