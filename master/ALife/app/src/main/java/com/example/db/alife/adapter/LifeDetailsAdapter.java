package com.example.db.alife.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.alife.R;
import com.example.db.alife.beans.LifeDetailsInfo;
import com.example.db.alife.beans.WorldDetailsInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by db on 7/9/15.
 */
public class LifeDetailsAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<LifeDetailsInfo> lifeDetailsInfos;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;


    public LifeDetailsAdapter(Context context,ArrayList<LifeDetailsInfo> lifeDetailsInfos){
        super();
        this.context = context;
        this.lifeDetailsInfos = lifeDetailsInfos;


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
        return lifeDetailsInfos.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.world_details_item,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.picture = (ImageView)convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final LifeDetailsInfo lifeDetailsInfo = lifeDetailsInfos.get(position);
        viewHolder.title.setText("    "+lifeDetailsInfo.getTitle());

        imageLoader.displayImage(lifeDetailsInfo.getPicture(),viewHolder.picture,options);

        return convertView;
    }
    public static class ViewHolder{
        TextView title;
        ImageView picture;
    }
}
