package com.example.db.messagewall.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMFileMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.db.messagewall.activity.FileDetailsActivity;
import com.example.db.messagewall.activity.PictureDetailsActivity;
import com.example.db.messagewall.activity.TextDetailsActivity;
import com.example.db.messagewall.activity.VoiceDetailsActivity;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.CircleButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.support.android.designlibdemo.R;

import java.util.List;

/**
 * Created by db on 7/14/15.
 */
public class MessageGridAdapter extends BaseAdapter{

    public Context context;
    public List<AVIMTypedMessage> avimMessages;

    public MessageGridAdapter(Context context){
        super();
        this.context = context;
    }

    public void setAvimMessages(List<AVIMTypedMessage> avimMessages){
        this.avimMessages = avimMessages;
    }

    public List<AVIMTypedMessage> getAvimMessages(){
        return this.avimMessages;
    }

    public void addAvimMessage(AVIMTypedMessage avimMessage){
        avimMessages.add(avimMessage);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return avimMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return avimMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
        根据消息类型的不同，来显示不同的消息
         */
        final AVIMTypedMessage avimMessage = avimMessages.get(position);

        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.message_wall_item,null);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView)convertView.findViewById(R.id.date);
            viewHolder.date0 = (TextView)convertView.findViewById(R.id.date0);
            viewHolder.from = (TextView)convertView.findViewById(R.id.from);
            viewHolder.circleButton = (CircleButton)convertView.findViewById(R.id.wall_item);
            viewHolder.linearLayout = (LinearLayout)convertView.findViewById(R.id.bkg);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        position = avimMessages.size()-position-1;

        /*
        显示留言条的留言纸
         */
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.db.alife_wallitempaper", Context.MODE_PRIVATE);
        String paper = sharedPreferences.getString("paper_path","");
        if (paper.equals("")){

        }else {
            Bitmap bitmap = BitmapFactory.decodeFile(paper);
            if (bitmap==null){
            }else {

            }
            Drawable drawable = new BitmapDrawable(bitmap);
        }


        /*
        不同类型的消息显示不同
        -1代表文字消息
        -2代表图片消息
        -3代表语音消息
        -6代表文件消息
         */
        switch (avimMessage.getMessageType()){
            case -1:
                AVIMTextMessage avimTextMessage = (AVIMTextMessage)avimMessage;
                viewHolder.from.setText("来自: "+avimTextMessage.getFrom().toString());
                viewHolder.date0.setText("时间: "+AppConstant.convertTime(avimTextMessage.getTimestamp()));
                viewHolder.circleButton.setImageResource(R.drawable.ic_edit_white_24dp);
                viewHolder.circleButton.setColor(Color.parseColor("#58d996"));
                viewHolder.date.setText("文字");
                break;
            case -2:
                AVIMImageMessage avimImageMessage = (AVIMImageMessage)avimMessage;
                viewHolder.from.setText("来自: "+avimImageMessage.getFrom().toString());
                viewHolder.date0.setText("时间: "+AppConstant.convertTime(avimImageMessage.getTimestamp()));
                viewHolder.circleButton.setImageResource(R.drawable.ic_crop_original_white_24dp);
                viewHolder.circleButton.setColor(Color.parseColor("#01c2e1"));
                viewHolder.date.setText("图片");
                break;
            case -3:
                AVIMAudioMessage avimAudioMessage = (AVIMAudioMessage)avimMessage;
                viewHolder.from.setText("来自: "+avimAudioMessage.getFrom().toString());
                viewHolder.date0.setText("时间: "+AppConstant.convertTime(avimAudioMessage.getTimestamp()));
                viewHolder.circleButton.setImageResource(R.drawable.abc_ic_voice_search_api_mtrl_alpha);
                viewHolder.circleButton.setColor(Color.parseColor("#e45779"));
                viewHolder.date.setText("语音");
                break;
            case -6:
                AVIMFileMessage avimFileMessage = (AVIMFileMessage)avimMessage;
                viewHolder.date0.setText("时间: "+AppConstant.convertTime(avimFileMessage.getTimestamp()));
                viewHolder.from.setText("来自: "+avimFileMessage.getFrom().toString());
                viewHolder.circleButton.setImageResource(R.drawable.ic_insert_link_white_24dp);
                viewHolder.circleButton.setColor(Color.parseColor("#e9c04d"));
                viewHolder.date.setText("文件");
                break;
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (avimMessage.getMessageType()){
                    case -1:
                        AVIMTextMessage avimTextMessage = (AVIMTextMessage)avimMessage;
                        Intent intent = new Intent(context, TextDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putString("content",avimTextMessage.getText().toString());
                        bundle.putString("from",avimTextMessage.getFrom().toString());
                        bundle.putString("date",AppConstant.convertTime(avimTextMessage.getTimestamp()));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        break;
                    case -2:
                        AVIMImageMessage avimImageMessage = (AVIMImageMessage)avimMessage;
                        Intent intent0 = new Intent(context, PictureDetailsActivity.class);
                        intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle0 = new Bundle();
                        bundle0.putString("content",avimImageMessage.getText().toString());
                        bundle0.putString("from",avimImageMessage.getFrom().toString());
                        bundle0.putString("date",AppConstant.convertTime(avimImageMessage.getTimestamp()));
                        bundle0.putString("url",avimImageMessage.getFileUrl());
                        intent0.putExtras(bundle0);
                        context.startActivity(intent0);
                        break;
                    case -3:
                        AVIMAudioMessage avimAudioMessage = (AVIMAudioMessage)avimMessage;
                        Intent intent2 = new Intent(context, VoiceDetailsActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("content",avimAudioMessage.getText().toString());
                        bundle2.putString("from",avimAudioMessage.getFrom().toString());
                        bundle2.putString("date",AppConstant.convertTime(avimAudioMessage.getTimestamp()));
                        bundle2.putString("url",avimAudioMessage.getFileUrl());
                        bundle2.putString("msgId",avimAudioMessage.getMessageId());
                        intent2.putExtras(bundle2);
                        context.startActivity(intent2);
                        break;
                    case -6:
                        AVIMFileMessage avimFileMessage = (AVIMFileMessage)avimMessage;
                        Intent intent1 = new Intent(context, FileDetailsActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("content",avimFileMessage.getText().toString());
                        bundle1.putString("from",avimFileMessage.getFrom().toString());
                        bundle1.putString("date",AppConstant.convertTime(avimFileMessage.getTimestamp()));
                        bundle1.putString("url",avimFileMessage.getFileUrl());
                        intent1.putExtras(bundle1);
                        context.startActivity(intent1);
                        break;
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder{
        TextView date,from,date0;
        CircleButton circleButton;
        LinearLayout linearLayout;
    }
}
