package com.example.db.messagewall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationMemberCountCallback;
import com.example.db.messagewall.activity.MainActivity;
import com.example.db.messagewall.bean.WallInfo;
import com.example.db.messagewall.view.CircleImageView;
import com.support.android.designlibdemo.R;

import java.util.List;

/**
 * Created by db on 7/17/15.
 */
public class WallSwipeAdapter extends BaseAdapter {

    public Context context;
    public List<AVIMConversation> avimConversations;

    public WallSwipeAdapter(Context context,List<AVIMConversation> avimConversations){
        super();
        this.context = context;
        this.avimConversations = avimConversations;
    }

    @Override
    public int getCount() {
        return avimConversations.size();
    }

    @Override
    public Object getItem(int position) {
        return avimConversations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.wall_item,null);
            viewHolder = new ViewHolder();
            viewHolder.circleImageView = (CircleImageView)convertView.findViewById(R.id.img);
            viewHolder.count = (TextView)convertView.findViewById(R.id.wall_count);
            viewHolder.description = (TextView)convertView.findViewById(R.id.wall_description);
//            viewHolder.linearLayout = (LinearLayout)convertView.findViewById(R.id.circle);
            viewHolder.name = (TextView)convertView.findViewById(R.id.wall_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final AVIMConversation avimConversation = avimConversations.get(position);

        avimConversation.getMemberCount(new AVIMConversationMemberCountCallback() {
            @Override
            public void done(Integer integer, AVException e) {
                if (e==null){
                    viewHolder.count.setText(integer+"人");
                }else {
                    viewHolder.count.setText("1人");
                }
            }
        });
        viewHolder.name.setText(avimConversation.getName());
        viewHolder.description.setText(avimConversation.getAttribute("description").toString());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("_ID",avimConversation.getConversationId());
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public static class ViewHolder {

        CircleImageView circleImageView;
        TextView name,description,count;
        LinearLayout linearLayout;

    }

}
