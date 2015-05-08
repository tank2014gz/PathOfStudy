package com.example.db.tline.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.db.tline.MainActivity;
import com.example.db.tline.R;
import com.example.db.tline.activity.TLineDetailsActivity;
import com.example.db.tline.beans.TLineListInfo;
import com.example.db.tline.database.TLineListSQLiDateBaseHelper;

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
            viewHolder.mDivider=(LinearLayout)view.findViewById(R.id.divide);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        if (i==tLineListInfos.size()-1){
            viewHolder.mDivider.setVisibility(View.GONE);
        }else {
            viewHolder.mDivider.setVisibility(View.VISIBLE);
        }
        final int temp=i;
        viewHolder.mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TLineListSQLiDateBaseHelper tLineListSQLiDateBaseHelper=new TLineListSQLiDateBaseHelper(context);
                SQLiteDatabase sqLiteDatabase=tLineListSQLiDateBaseHelper.getWritableDatabase();
                sqLiteDatabase.delete("textlinelist","title like ?",new String[]{tLineListInfos.get(temp).getTitle()});
                sqLiteDatabase.close();
                notifyDataSetChanged();

                Intent intent=new Intent(context, TLineDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle =new Bundle();
                bundle.putString("title",tLineListInfos.get(temp).getRelationtitle());
                bundle.putString("content",tLineListInfos.get(temp).getRelationcontent());
                bundle.putString("date",tLineListInfos.get(temp).getRelationdate());
                intent.putExtra("adapter",bundle);
                context.startActivity(intent);
            }
        });

        TLineListInfo tLineListInfo=tLineListInfos.get(i);
        viewHolder.mTitle.setText(tLineListInfo.getTitle());
        viewHolder.mContent.setText(tLineListInfo.getContent());

        return view;
    }

    public static class ViewHolder{
        TextView mTitle,mContent,mDate;
        LinearLayout mDivider;
    }
}
