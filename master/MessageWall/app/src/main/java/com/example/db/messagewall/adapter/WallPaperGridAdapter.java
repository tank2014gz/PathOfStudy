package com.example.db.messagewall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.db.messagewall.api.AppData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 7/18/15.
 */
public class WallPaperGridAdapter extends BaseAdapter{

    public Context context;
    public List<String> list = new ArrayList<String>();

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public WallPaperGridAdapter(Context context){
        super();
        this.context = context;
        initList();

        imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.photo3)
                .showImageForEmptyUri(R.drawable.photo3)
                .showImageOnFail(R.drawable.photo3)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }

    @Override
    public int getCount() {
        return list.size()+1;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.paper_item,null);
            viewHolder = new ViewHolder();
            viewHolder.linearLayout = (LinearLayout)convertView.findViewById(R.id.btn_share);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.img_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (position==list.size()){
            viewHolder.imageView.setImageResource(R.drawable.compose_pic_add_highlighted);
        }else {
            imageLoader.displayImage(list.get(position),viewHolder.imageView,options);
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                viewHolder.linearLayout.setVisibility(ViewGroup.VISIBLE);
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
        LinearLayout linearLayout;
        ImageView imageView;
    }

    public void initList(){
        list.add("http://upload.jianshu.io/daily_images/images/Km3pAVJX9t3zt782a6p9.jpg");
        list.add("http://upload.jianshu.io/daily_images/images/Uedq7xEzs8YdpcHUjKpR.jpg");
        list.add("http://upload-images.jianshu.io/upload_images/4-76dfbc77637a82e7.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/1240");

    }
}
