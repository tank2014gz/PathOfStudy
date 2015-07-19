package com.example.db.messagewall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.db.messagewall.activity.MainActivity;
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

    public boolean flag = false;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.paper_item,null);
            viewHolder = new ViewHolder();
            viewHolder.linearLayout = (LinearLayout)convertView.findViewById(R.id.btn_share);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.img_item);
            viewHolder.download = (LinearLayout)convertView.findViewById(R.id.btn_download);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (position==list.size()){

            viewHolder.imageView.setImageResource(R.drawable.compose_pic_add_highlighted);
            viewHolder.download.setVisibility(ViewGroup.GONE);
        }else {
            imageLoader.displayImage(list.get(position),viewHolder.imageView,options);
            viewHolder.download.setVisibility(ViewGroup.VISIBLE);
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position!=list.size()){
                    if (viewHolder.linearLayout.getVisibility()==ViewGroup.VISIBLE){

                        viewHolder  .linearLayout.setVisibility(ViewGroup.GONE);
                        flag = false;

                    }else if (viewHolder.linearLayout.getVisibility()==ViewGroup.GONE&&flag==false){

                        viewHolder.linearLayout.setVisibility(ViewGroup.VISIBLE);
                        flag = true;
                    }
                }else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    ((MainActivity)context).startActivityForResult(intent,2);
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder{
        LinearLayout linearLayout,download;
        ImageView imageView;
    }

    public void initList(){
        list.add("http://i10.topitme.com/l019/10019082022d55c5f8.jpg");
        list.add("http://i10.topitme.com/l019/100190830222f6d2b8.jpg");
        list.add("http://i10.topitme.com/l019/1001908229cd19a9ab.jpg");
        list.add("http://i10.topitme.com/l019/100190815685414412.jpg");
        list.add("http://i10.topitme.com/l019/10019081290e4eaf34.jpg");
        list.add("http://i10.topitme.com/l019/10019081332d6b63d7.jpg");
        list.add("http://i10.topitme.com/l019/1001908213613400dc.jpg");
        list.add("http://f10.topitme.com/l019/100190816957f620a7.jpg");


    }
}
