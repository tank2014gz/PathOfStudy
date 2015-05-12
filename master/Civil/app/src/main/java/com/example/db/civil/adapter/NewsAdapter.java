package com.example.db.civil.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.civil.R;
import com.example.db.civil.activity.SoftWareItemActivity;
import com.example.db.civil.beans.NewsInfo;
import com.example.db.civil.beans.SoftWareInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by db on 5/11/15.
 */
public class NewsAdapter extends BaseAdapter{
    public Context context;

    public ArrayList<Drawable> list=new ArrayList<Drawable>();

    public ArrayList<NewsInfo> newsInfos;

    public NewsAdapter(Context context,ArrayList<NewsInfo> newsInfos){
        super();
        this.context=context;
        this.newsInfos=newsInfos;
        initImage();
    }

    @Override
    public int getCount() {
        return newsInfos.size();
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

        Random random=new Random();

        viewHolder.imageView.setImageDrawable(list.get(random.nextInt(10)));

        if (newsInfos.get(i).getTitle()!=null){
            viewHolder.name.setText(newsInfos.get(i).getTitle());
        }else {
            viewHolder.name.setText("未找到内容");
        }

        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);

        viewHolder.title.setText(date);

        final int temp=i;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newsInfos.get(temp).getUrl().length()!=0){
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(newsInfos.get(temp).getUrl()));
                    context.startActivity(intent);
                }
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
