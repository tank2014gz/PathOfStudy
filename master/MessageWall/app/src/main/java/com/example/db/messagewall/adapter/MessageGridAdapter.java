package com.example.db.messagewall.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.example.db.messagewall.utils.AppConstant;
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
    public List<AVIMMessage> avimMessages;

    public MessageGridAdapter(Context context){
        super();
        this.context = context;
    }

    public void setAvimMessages(List<AVIMMessage> avimMessages){
        this.avimMessages = avimMessages;
    }

    public List<AVIMMessage> getAvimMessages(){
        return this.avimMessages;
    }

    public void addAvimMessage(AVIMMessage avimMessage){
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


        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.message_wall_item,null);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView)convertView.findViewById(R.id.date);
            viewHolder.content = (TextView)convertView.findViewById(R.id.content);
            viewHolder.from = (TextView)convertView.findViewById(R.id.from);
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

        AVIMMessage avimMessage = avimMessages.get(position);
        viewHolder.from.setText("----   "+avimMessage.getFrom().toString());
        viewHolder.content.setText("    "+avimMessage.getContent().toString());
        viewHolder.date.setText(AppConstant.convertTime(avimMessage.getTimestamp()));
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    public static class ViewHolder{
        TextView date,content,from;
        LinearLayout linearLayout;
    }

}
