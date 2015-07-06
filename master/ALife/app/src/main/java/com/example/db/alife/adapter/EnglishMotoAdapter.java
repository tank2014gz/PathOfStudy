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
import com.example.db.alife.beans.EnglishMotoInfo;
import com.example.db.alife.fragment.EnglishMoto;
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

    public EnglishMotoAdapter(Context context,ArrayList<EnglishMotoInfo> englishMotoInfos){
        super();
        this.context = context;
        this.englishMotoInfos = englishMotoInfos;

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
            viewHolder.content = (TextView)convertView.findViewById(R.id.description);
            viewHolder.picture = (ImageView)convertView.findViewById(R.id.picture);
            viewHolder.tag = (TextView)convertView.findViewById(R.id.tag);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        EnglishMotoInfo englishMotoInfo = englishMotoInfos.get(position);
        viewHolder.title.setText(englishMotoInfo.getTitle());
        viewHolder.content.setText("    "+englishMotoInfo.getDescription());
        viewHolder.tag.setText(englishMotoInfo.getTag());

        imageLoader.displayImage(englishMotoInfo.getPicture(),viewHolder.picture,options);

        return convertView;
    }
    public static class ViewHolder{
        TextView title,content,tag;
        ImageView picture;
    }
}
