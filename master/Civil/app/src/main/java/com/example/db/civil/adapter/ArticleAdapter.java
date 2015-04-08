package com.example.db.civil.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.civil.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 4/7/15.
 */
public class ArticleAdapter extends BaseAdapter {

    public Context context;
    public List<String> strings=new ArrayList<String>();
    public List<Integer> integers=new ArrayList<Integer>();

    public DisplayImageOptions options;

    public ImageLoader imageLoader;

    public ArticleAdapter(Context context){
        super();
        this.context=context;

        imageLoader=ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.empty_picture)
                .showImageForEmptyUri(R.drawable.empty_picture)
                .showImageOnFail(R.drawable.empty_failed)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        strings.add("华中科技大学西十二");
        strings.add("华中科技大学东九");
        integers.add(R.drawable.test);
        integers.add(R.drawable.test);

    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.article_item,null);
            viewHolder=new ViewHolder();
            viewHolder.preview=(ImageView)view.findViewById(R.id.preview);
            viewHolder.from=(TextView)view.findViewById(R.id.description);
            viewHolder.description=(TextView)view.findViewById(R.id.from);
            viewHolder.collection=(Button)view.findViewById(R.id.collection);
            viewHolder.comment=(Button)view.findViewById(R.id.comment);
            viewHolder.share=(Button)view.findViewById(R.id.share);
            viewHolder.more=(Button)view.findViewById(R.id.more);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        imageLoader.displayImage("http://www.edu24ol.com/web_news/images/2013-01/20130115025237509250.jpg", viewHolder.preview, options);

//        viewHolder.preview.setBackground(context.getResources().getDrawable(integers.get(i)));

        return view;
    }

    public static class ViewHolder{
        TextView from,description;
        ImageView preview;
        Button collection,comment,share,more;

    }
}
