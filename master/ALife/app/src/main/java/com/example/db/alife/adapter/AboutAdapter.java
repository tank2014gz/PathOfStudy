package com.example.db.alife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db.alife.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by db on 7/9/15.
 */
public class AboutAdapter extends BaseAdapter{

    public Context context;

    public AboutAdapter(Context context){
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.about_item,null);
            viewHolder = new ViewHolder();
            viewHolder.circleImageView = (CircleImageView)convertView.findViewById(R.id.img);
            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.description = (TextView)convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        switch (position){
            case 0:
                viewHolder.circleImageView.setImageResource(R.drawable.logo0);
                viewHolder.name.setText("KG@Hust");
                viewHolder.description.setText("Android Coder  大二党");
                break;
            case 1:
                viewHolder.circleImageView.setImageResource(R.drawable.logo1);
                viewHolder.name.setText("第n陌");
                viewHolder.description.setText("Android Coder  大二党");
                break;
            case 2:
                viewHolder.circleImageView.setImageResource(R.drawable.logo2);
                viewHolder.name.setText("小暖");
                viewHolder.description.setText("Designer  大二党");
                break;
        }

        return convertView;
    }
    public static class ViewHolder{
        CircleImageView circleImageView;
        TextView name,description;
    }
}