package com.example.db.alife.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.alife.R;
import com.example.db.alife.activity.SentenceDetailsActivity;
import com.example.db.alife.activity.WorldDetailsActivity;
import com.example.db.alife.beans.EnglishMotoInfo;
import com.example.db.alife.beans.ShootWorldInfo;
import com.example.db.alife.view.ExpandableTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by db on 7/7/15.
 */
public class ShootWorldAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<ShootWorldInfo> shootWorldInfos;

    public String description = "因为摄影，我让相机改变了自己。\n" +
            "因为摄影，我学会了欣赏。学会了欣赏美的生活、美的环境、美的人。\n" +
            "因为摄影，我习惯早起。天不亮就会醒，看看窗外的天空，如果蓝天白云，会立即起身，背起相机就去寻找“制高点”。\n" +
            "因为摄影，我会用多彩的角度欣赏世界：我会在春花烂漫里举起相机；我会记录下夏天暴风雨的怒吼；我会收获金秋沉甸甸的繁荣；我会欣赏冬天的肃穆和纯洁。\n";

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public final SparseBooleanArray mCollapsedStatus;

    public ShootWorldAdapter(Context context,ArrayList<ShootWorldInfo> shootWorldInfos){
        super();
        this.context = context;
        this.shootWorldInfos = shootWorldInfos;

        mCollapsedStatus = new SparseBooleanArray();

        imageLoader=ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.rule4)
                .showImageForEmptyUri(R.drawable.rule4)
                .showImageOnFail(R.drawable.rule4)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }

    @Override
    public int getCount() {
        return shootWorldInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.world_item,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.picture = (ImageView)convertView.findViewById(R.id.picture);
            viewHolder.tag = (TextView)convertView.findViewById(R.id.tag);
            viewHolder.date = (TextView)convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final ShootWorldInfo shootWorldInfo = shootWorldInfos.get(position);
        viewHolder.title.setText(shootWorldInfo.getTitle());
        viewHolder.tag.setText(shootWorldInfo.getTag());
        viewHolder.date.setText(shootWorldInfo.getDate());
        imageLoader.displayImage(shootWorldInfo.getPicture(),viewHolder.picture,options);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(context, WorldDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", shootWorldInfo.getUrl());
                bundle.putString("picture", shootWorldInfo.getPicture());
                bundle.putString("description", description);
                bundle.putString("title", shootWorldInfo.getTitle());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

        return convertView;
    }
    public static class ViewHolder{
        TextView title,tag,date;
        ImageView picture;
    }
}
