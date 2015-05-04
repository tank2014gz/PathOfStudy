package com.example.db.tline.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.db.tline.R;
import com.example.db.tline.beans.PLineListInfo;
import com.example.db.tline.beans.TLineListInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by db on 5/1/15.
 */
public class PictureLineDetailsAdapter extends BaseAdapter{

    public Context context;
    public ArrayList<PLineListInfo> pLineListInfos;
    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public PictureLineDetailsAdapter(Context context,ArrayList<PLineListInfo> pLineListInfos){
        super();
        this.context=context;
        this.pLineListInfos=pLineListInfos;

        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.login)
                .showImageForEmptyUri(R.drawable.login)
                .showImageOnFail(R.drawable.login)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }

    @Override
    public int getCount() {
        return pLineListInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.list_picture,null);
            viewHolder=new ViewHolder();
            viewHolder.mTitle=(TextView)view.findViewById(R.id.title);
            viewHolder.mContent=(TextView)view.findViewById(R.id.content);
            viewHolder.mDate=(TextView)view.findViewById(R.id.date);
            viewHolder.mPicture=(ImageView)view.findViewById(R.id.picture);
            viewHolder.up=(LinearLayout)view.findViewById(R.id.up);
            viewHolder.down=(LinearLayout)view.findViewById(R.id.down);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        if (pLineListInfos.size()==1){
            viewHolder.up.setVisibility(View.GONE);
            viewHolder.down.setVisibility(View.GONE);
        }else {
            viewHolder.up.setVisibility(View.VISIBLE);
            viewHolder.down.setVisibility(View.VISIBLE);
        }

        if (i==0){
            viewHolder.up.setVisibility(View.GONE);
        }else if (i==pLineListInfos.size()-1){
            viewHolder.down.setVisibility(View.GONE);
        }else {
            viewHolder.up.setVisibility(View.VISIBLE);
            viewHolder.down.setVisibility(View.VISIBLE);
        }

        PLineListInfo pLineListInfo=pLineListInfos.get(i);
        viewHolder.mTitle.setText(pLineListInfo.getTitle());
        viewHolder.mContent.setText(pLineListInfo.getContent());

        if (pLineListInfo.getUrl().length()!=0){

                imageLoader.displayImage(pLineListInfo.getUrl(),viewHolder.mPicture, options);

        }

        return view;
    }
    public static class ViewHolder{
        TextView mTitle,mContent,mDate;
        LinearLayout up,down;
        ImageView mPicture;
    }
}
