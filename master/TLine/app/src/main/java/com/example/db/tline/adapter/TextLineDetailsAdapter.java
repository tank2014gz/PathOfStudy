package com.example.db.tline.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db.tline.R;
import com.example.db.tline.beans.TLineListInfo;

import java.util.ArrayList;

/**
 * Created by db on 4/23/15.
 */
public class TextLineDetailsAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<TLineListInfo> tLineListInfos=new ArrayList<TLineListInfo>();

    public TextLineDetailsAdapter(Context context,ArrayList<TLineListInfo> tLineListInfos){
        super();
        this.context=context;
        this.tLineListInfos=tLineListInfos;
    }

    @Override
    public int getCount() {
        return tLineListInfos.size();
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
            view= LayoutInflater.from(context).inflate(R.layout.list_text,null);
            viewHolder=new ViewHolder();
            viewHolder.mTitle=(TextView)view.findViewById(R.id.title);
            viewHolder.mContent=(TextView)view.findViewById(R.id.content);
            viewHolder.mDate=(TextView)view.findViewById(R.id.date);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        TLineListInfo tLineListInfo=tLineListInfos.get(i);
        viewHolder.mTitle.setText(tLineListInfo.getTitle());
        viewHolder.mContent.setText(tLineListInfo.getContent());

        return view;
    }

    public static class ViewHolder{
        TextView mTitle,mContent,mDate;
    }
}
