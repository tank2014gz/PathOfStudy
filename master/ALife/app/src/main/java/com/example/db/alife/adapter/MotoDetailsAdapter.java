package com.example.db.alife.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.alife.R;
import com.example.db.alife.beans.MotodetailsInfo;
import com.example.db.alife.view.ExpandableTextView;

import java.util.ArrayList;

/**
 * Created by db on 7/8/15.
 */
public class MotoDetailsAdapter extends BaseAdapter{

    public Context context;
    public ArrayList<MotodetailsInfo> motodetailsInfos;

    public final SparseBooleanArray mCollapsedStatus;

    public MotoDetailsAdapter(Context context,ArrayList<MotodetailsInfo> motodetailsInfos){
        super();
        this.context = context;
        this.motodetailsInfos = motodetailsInfos;

        mCollapsedStatus = new SparseBooleanArray();

    }

    @Override
    public int getCount() {
        return motodetailsInfos.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.moto_details_item,null);
            viewHolder = new ViewHolder();
            viewHolder.english = (ExpandableTextView)convertView.findViewById(R.id.expand_text_view);
            viewHolder.chinese = (ExpandableTextView)convertView.findViewById(R.id.expand_text_view0);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        MotodetailsInfo motodetailsInfo = motodetailsInfos.get(position);

        viewHolder.english.setText("    "+motodetailsInfo.getEnglish(),mCollapsedStatus,position);
        viewHolder.chinese.setText("    "+motodetailsInfo.getChinese(),mCollapsedStatus,position);

        return convertView;
    }
    public static class ViewHolder{
        ExpandableTextView chinese,english;
    }
}
