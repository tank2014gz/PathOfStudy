package com.example.db.messagewall.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.db.messagewall.view.CircleImageView;
import com.support.android.designlibdemo.R;

import java.util.List;

/**
 * Created by db on 8/11/15.
 */
public class CommitAdapter extends BaseAdapter {

    public Context context;

    public List<AVObject> list;

    public CommitAdapter(Context context,List<AVObject> list){
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.commit_item,null);
            viewHolder = new ViewHolder();
            viewHolder.from = (TextView)convertView.findViewById(R.id.from);
            viewHolder.content = (TextView)convertView.findViewById(R.id.content);
            viewHolder.to = (TextView)convertView.findViewById(R.id.to);
            viewHolder.date = (TextView)convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        AVObject avObject = list.get(position);

        /*
        显示昵称
         */
        AVQuery<AVObject> query = new AVQuery<AVObject>("NiChen");
        query.whereEqualTo("username", avObject.get("from").toString());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){
                    AVObject avObject0 = (AVObject)list.get(0);
                    if (avObject0.get("nichen").toString()!=null){
                        viewHolder.from.setText(avObject0.get("nichen").toString());
                    }else {
                        viewHolder.from.setText("查询昵称失败");
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });
        AVQuery<AVObject> query0 = new AVQuery<AVObject>("NiChen");
        query0.whereEqualTo("username", avObject.get("to").toString());
        query0.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){
                    AVObject avObject0 = (AVObject)list.get(0);
                    if (avObject0.get("nichen").toString()!=null){
                        viewHolder.to.setText(avObject0.get("nichen").toString());
                    }else {
                        viewHolder.to.setText("查询昵称失败");
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });

        viewHolder.content.setText(avObject.get("comment").toString());
        String date = avObject.getCreatedAt().toString().replace("Tue","").replace("GMT+08:00","");
        Log.v("date00000000000",date);
        viewHolder.date.setText(date);

        return convertView;
    }
    public static class ViewHolder{
        TextView from,content,to,date;
    }
}
