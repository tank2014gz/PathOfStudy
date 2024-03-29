package com.example.db.messagewall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationMemberCountCallback;
import com.example.db.messagewall.activity.MainActivity;
import com.example.db.messagewall.bean.WallInfo;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.CircleButton;
import com.example.db.messagewall.view.CircleImageView;
import com.example.db.messagewall.view.MaterialDialog;
import com.support.android.designlibdemo.R;

import java.util.List;

/**
 * Created by db on 7/21/15.
 */
public class AlterWallAdapter extends RecyclerView.Adapter<AlterWallAdapter.ViewHolder> {

    private List<WallInfo> items;
    public Context context;

    public List<AVIMConversation> avimConversations;

    public AlterWallAdapter(Context context,List<AVIMConversation> avimConversations){
        super();
        this.context = context;
        this.avimConversations = avimConversations;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final AVIMConversation avimConversation = avimConversations.get(position);
        avimConversation.getMemberCount(new AVIMConversationMemberCountCallback() {
            @Override
            public void done(Integer integer, AVException e) {
                if (e==null){
                    holder.count.setText(integer+"人");
                }else {
                    holder.count.setText("1人");
                }
            }
        });
        holder.name.setText(avimConversation.getName());
        holder.description.setText(avimConversation.getAttribute("description").toString());

        holder.textView.setText(avimConversation.getName().substring(0,1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return avimConversations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleButton circleButton;
        TextView name,description,count,textView;

        public ViewHolder(View itemView) {
            super(itemView);
            circleButton = (CircleButton)itemView.findViewById(R.id.circlebutton);
            name = (TextView)itemView.findViewById(R.id.wall_name);
            description = (TextView)itemView.findViewById(R.id.wall_description);
            count = (TextView)itemView.findViewById(R.id.wall_count);
            textView = (TextView)itemView.findViewById(R.id.text);
        }
    }

}
