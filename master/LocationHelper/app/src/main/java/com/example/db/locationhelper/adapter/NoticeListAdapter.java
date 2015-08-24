package com.example.db.locationhelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db.locationhelper.R;

/**
 * Created by db on 8/23/15.
 */
public class NoticeListAdapter extends BaseAdapter {

    private Context context;

    public NoticeListAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.notice_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_Content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.tv_Date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.tv_Status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    public static class ViewHolder {
        TextView tv_Content, tv_Date, tv_Status;
    }
}
