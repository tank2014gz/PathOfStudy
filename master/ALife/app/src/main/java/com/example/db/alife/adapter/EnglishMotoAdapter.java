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
import com.example.db.alife.activity.MotoDetailsActivity;
import com.example.db.alife.beans.EnglishMotoInfo;
import com.example.db.alife.fragment.EnglishMoto;
import com.example.db.alife.view.ExpandableTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by db on 7/6/15.
 */
public class EnglishMotoAdapter extends BaseAdapter{

    public Context context;
    public ArrayList<EnglishMotoInfo> englishMotoInfos;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public final SparseBooleanArray mCollapsedStatus;

    public EnglishMotoAdapter(Context context,ArrayList<EnglishMotoInfo> englishMotoInfos){
        super();
        this.context = context;
        this.englishMotoInfos = englishMotoInfos;

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
        return englishMotoInfos.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.moto_item,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.description = (ExpandableTextView)convertView.findViewById(R.id.expand_text_view);
            viewHolder.picture = (ImageView)convertView.findViewById(R.id.picture);
            viewHolder.tag = (TextView)convertView.findViewById(R.id.tag);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final EnglishMotoInfo englishMotoInfo = englishMotoInfos.get(position);
        viewHolder.title.setText(englishMotoInfo.getTitle());
        viewHolder.description.setText("    "+englishMotoInfo.getDescription(),mCollapsedStatus,position);
        viewHolder.tag.setText(englishMotoInfo.getTag());

        imageLoader.displayImage(englishMotoInfo.getPicture(),viewHolder.picture,options);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(context, MotoDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",englishMotoInfo.getUrl());
                bundle.putString("picture",englishMotoInfo.getPicture());
                bundle.putString("description",englishMotoInfo.getDescription());
                bundle.putString("title",englishMotoInfo.getTitle());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

        return convertView;
    }
    public static class ViewHolder{
        TextView title,tag;
        ExpandableTextView description;
        ImageView picture;
    }
}
