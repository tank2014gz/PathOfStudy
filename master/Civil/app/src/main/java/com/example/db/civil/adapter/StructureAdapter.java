package com.example.db.civil.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.civil.R;
import com.example.db.civil.activity.RuleItemActivity;
import com.example.db.civil.beans.RuleItem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by db on 4/12/15.
 */
public class StructureAdapter extends BaseAdapter {

    public ArrayList<RuleItem> ruleItems;

    public Context context;

    public ArrayList<Drawable> list=new ArrayList<Drawable>();

    public StructureAdapter(Context context,ArrayList<RuleItem> ruleItems){
        super();
        this.context=context;
        this.ruleItems=ruleItems;

        initImage();

    }

    @Override
    public int getCount() {
        return ruleItems.size();
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
            view= LayoutInflater.from(context).inflate(R.layout.rule_adapter,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView=(ImageView)view.findViewById(R.id.preview);
            viewHolder.name=(TextView)view.findViewById(R.id.name);
            viewHolder.title=(TextView)view.findViewById(R.id.time);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        Log.v("mbab", ruleItems.get(i).getTitle());

        Random random=new Random();

        viewHolder.imageView.setImageDrawable(list.get(random.nextInt(10)));

        if (ruleItems.get(i).getTitle()!=null){
            viewHolder.name.setText(ruleItems.get(i).getTitle());
        }else {
            viewHolder.name.setText("未找到内容");
        }
        if(ruleItems.get(i).getDate()!=null){
            viewHolder.title.setText(ruleItems.get(i).getDate());
        }else {
            viewHolder.title.setText("未找到内容");
        }

        final int temp=i;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(context, RuleItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("url",ruleItems.get(temp).getUrl());
                bundle.putString("name",ruleItems.get(temp).getTitle());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return view;
    }

    public static class ViewHolder{
        ImageView imageView;
        TextView name,title;
    }

    public void initImage(){
        list.add(context.getResources().getDrawable(R.drawable.rule1));
        list.add(context.getResources().getDrawable(R.drawable.rule2));
        list.add(context.getResources().getDrawable(R.drawable.rule3));
        list.add(context.getResources().getDrawable(R.drawable.rule4));
        list.add(context.getResources().getDrawable(R.drawable.rule5));
        list.add(context.getResources().getDrawable(R.drawable.rule6));
        list.add(context.getResources().getDrawable(R.drawable.rule7));
        list.add(context.getResources().getDrawable(R.drawable.rule8));
        list.add(context.getResources().getDrawable(R.drawable.rule9));
        list.add(context.getResources().getDrawable(R.drawable.empty));
        list.add(context.getResources().getDrawable(R.drawable.error));

    }

}
