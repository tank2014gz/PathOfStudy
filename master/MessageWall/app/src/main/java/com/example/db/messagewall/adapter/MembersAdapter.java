package com.example.db.messagewall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db.messagewall.bean.MemberInfo;
import com.example.db.messagewall.view.CircleButton;
import com.example.db.messagewall.view.CircleImageView;
import com.support.android.designlibdemo.R;

import java.util.List;


/**
 * Created by db on 7/9/15.
 */
public class MembersAdapter extends BaseAdapter{

    public Context context;
    public List<MemberInfo> memberInfos;

    public MembersAdapter(Context context,List<MemberInfo> memberInfos){
        super();
        this.context = context;
        this.memberInfos = memberInfos;
    }

    @Override
    public int getCount() {
        return memberInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return memberInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.members_item,null);
            viewHolder = new ViewHolder();
            viewHolder.circleButton = (CircleButton)convertView.findViewById(R.id.img);
            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.description = (TextView)convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        MemberInfo memberInfo = memberInfos.get(position);

        viewHolder.name.setText(memberInfo.getName());
        viewHolder.description.setText("来源于:"+memberInfo.getDate());

        return convertView;
    }
    public static class ViewHolder{
        TextView name,description;
        CircleButton circleButton;
    }
}
