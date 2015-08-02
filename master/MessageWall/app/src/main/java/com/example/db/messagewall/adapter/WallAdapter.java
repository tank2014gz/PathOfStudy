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
import com.avos.avoscloud.im.v2.Conversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationMemberCountCallback;
import com.example.db.messagewall.activity.SelectActivity;
import com.example.db.messagewall.bean.WallInfo;
import com.example.db.messagewall.activity.MainActivity;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.CircleButton;
import com.example.db.messagewall.view.CircleImageView;
import com.example.db.messagewall.view.MaterialDialog;
import com.support.android.designlibdemo.R;

import java.util.List;

/**
 * Created by db on 7/13/15.
 */
public class WallAdapter extends RecyclerView.Adapter<WallAdapter.ViewHolder> {

    private List<WallInfo> items;
    public Context context;

    public List<AVIMConversation> avimConversations;

    public WallAdapter(Context context,List<AVIMConversation> avimConversations){
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final MaterialDialog materialDialog = new MaterialDialog(context);
                materialDialog.setTitle("确定退出?")
                        .setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                avimConversation.quit(new AVIMConversationCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e==null){
                                            AppConstant.showSelfToast(context,"退出成功！");
                                        }else {
                                            AppConstant.showSelfToast(context,"退出失败！");
                                        }
                                    }
                                });
                                materialDialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                materialDialog.dismiss();
                            }
                        })
                        .setCanceledOnTouchOutside(true)
                        .setMessage("将不再接受消息")
                        .show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return avimConversations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,description,count;
        CircleButton circleButton;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.wall_name);
            description = (TextView)itemView.findViewById(R.id.wall_description);
            count = (TextView)itemView.findViewById(R.id.wall_count);
            circleButton = (CircleButton)itemView.findViewById(R.id.circlebutton);
        }
    }

}
