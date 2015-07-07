package com.example.db.alife.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.alife.R;
import com.example.db.alife.beans.BeautifulSentenceInfo;
import com.example.db.alife.view.ExpandableTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by db on 7/7/15.
 */
public class BeautifulSentenceAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<BeautifulSentenceInfo> beautifulSentenceInfos;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public final SparseBooleanArray mCollapsedStatus;

    public BeautifulSentenceAdapter(Context context,ArrayList<BeautifulSentenceInfo> beautifulSentenceInfos){
        super();
        this.context = context;
        this.beautifulSentenceInfos = beautifulSentenceInfos;

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
        return beautifulSentenceInfos.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.sentence_item,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.description = (ExpandableTextView)convertView.findViewById(R.id.expand_text_view);
            viewHolder.picture = (ImageView)convertView.findViewById(R.id.picture);
            viewHolder.tag = (TextView)convertView.findViewById(R.id.tag);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        BeautifulSentenceInfo beautifulSentenceInfo = beautifulSentenceInfos.get(position);
        viewHolder.title.setText(beautifulSentenceInfo.getTitle());
        viewHolder.description.setText("    "+beautifulSentenceInfo.getDescription(),mCollapsedStatus,position);
        viewHolder.tag.setText(beautifulSentenceInfo.getTag());

        imageLoader.displayImage(beautifulSentenceInfo.getPicture(),viewHolder.picture,options);

        return convertView;
    }
    public static class ViewHolder{
        TextView title,tag;
        ExpandableTextView description;
        ImageView picture;
    }
}
