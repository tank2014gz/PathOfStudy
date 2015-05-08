package com.example.db.tline.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.db.tline.MainActivity;
import com.example.db.tline.R;
import com.example.db.tline.activity.TLineDetailsActivity;
import com.example.db.tline.beans.TextLineInfo;
import com.example.db.tline.database.TLineSQLiDataBaseHelper;

import java.util.ArrayList;

/**
 * Created by db on 4/21/15.
 */
public class TextTimeLineAdapter extends BaseAdapter {


    public Context context;
    public ArrayList<TextLineInfo> textLineInfos;

    public TextTimeLineAdapter(Context context,ArrayList<TextLineInfo> textLineInfos){
        super();
        this.context=context;
        this.textLineInfos=textLineInfos;

    }

    @Override
    public int getCount() {
        return textLineInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return textLineInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.text_line_item,null);
            viewHolder=new ViewHolder();
            viewHolder.mTitle=(TextView)view.findViewById(R.id.title);
            viewHolder.mClock=(TextView)view.findViewById(R.id.clock);
            viewHolder.mDescription=(TextView)view.findViewById(R.id.description);
            viewHolder.mShare=(Button)view.findViewById(R.id.btn_share);
            viewHolder.mEdit=(Button)view.findViewById(R.id.btn_edit);
            viewHolder.mLocation=(TextView)view.findViewById(R.id.location);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        final TextLineInfo textLineInfo=textLineInfos.get(i);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, TLineDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle =new Bundle();
                bundle.putString("title",textLineInfo.getTitle().toString());
                bundle.putString("content",textLineInfo.getContent().toString());
                bundle.putString("date",textLineInfo.getDate().toString());
                intent.putExtra("adapter",bundle);
                context.startActivity(intent);
            }
        });
        if (textLineInfo.getLocation().length()!=0){
            viewHolder.mLocation.setText(textLineInfo.getLocation().toString());
        }

        viewHolder.mTitle.setText(textLineInfo.getTitle());
        viewHolder.mDescription.setText(textLineInfo.getContent());
        viewHolder.mClock.setText(textLineInfo.getDate());

        viewHolder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "share");
                intent.putExtra(Intent.EXTRA_TEXT, "share");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent,"share"));
            }
        });
        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TLineSQLiDataBaseHelper tLineSQLiDataBaseHelper=new TLineSQLiDataBaseHelper(context);
                SQLiteDatabase sqLiteDatabase=tLineSQLiDataBaseHelper.getWritableDatabase();
                sqLiteDatabase.delete("textline","title like ?",new String[]{textLineInfo.getTitle()});
                sqLiteDatabase.close();
                notifyDataSetChanged();

                Intent intent=new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
    public static class ViewHolder{
        TextView mTitle,mClock,mDescription,mLocation;
        Button mShare,mEdit,mMsg;
    }
}
