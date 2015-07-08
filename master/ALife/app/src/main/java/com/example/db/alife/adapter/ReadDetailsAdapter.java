package com.example.db.alife.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db.alife.R;
import com.example.db.alife.beans.ReadDetailsInfo;

import java.util.ArrayList;

/**
 * Created by db on 7/8/15.
 */
public class ReadDetailsAdapter extends BaseAdapter{

    public Context context;
    public ArrayList<ReadDetailsInfo> readDetailsInfos;


    public ReadDetailsAdapter(Context context,ArrayList<ReadDetailsInfo> readDetailsInfos){

        super();
        this.context = context;
        this.readDetailsInfos = readDetailsInfos;
    }

    @Override
    public int getCount() {
        return readDetailsInfos.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.read_details_item,null);
            viewHolder = new ViewHolder();
            viewHolder.english = (TextView)convertView.findViewById(R.id.english);
            viewHolder.chinese = (TextView)convertView.findViewById(R.id.chinese);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        ReadDetailsInfo readDetailsInfo = readDetailsInfos.get(position);

        viewHolder.english.setText("    "+readDetailsInfo.getEnglish());
        viewHolder.chinese.setText("    "+readDetailsInfo.getChinese());

        return convertView;
    }
    public static class ViewHolder{
        TextView chinese,english;
    }
}
