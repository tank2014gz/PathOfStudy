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
import com.example.db.civil.activity.RuleDetailActivity;
import com.example.db.civil.activity.SoftDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 4/14/15.
 */
public class SoftWareAdapter extends BaseAdapter {

    public Context context;

    public List<Drawable> list=new ArrayList<Drawable>();

    public List<String> kind=new ArrayList<String>();

    public List<String> ruleUrl=new ArrayList<String>();

    public SoftWareAdapter(Context context){
        super();
        this.context=context;

        initImage();

    }


    @Override
    public int getCount() {
        return 8;
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
            view= LayoutInflater.from(context).inflate(R.layout.rule_item,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView=(ImageView)view.findViewById(R.id.rule_bk);
            viewHolder.textView=(TextView)view.findViewById(R.id.rule_kind);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.textView.setText(kind.get(i));
        viewHolder.imageView.setImageDrawable(list.get(i));

        final int tem=i;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, SoftDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("ruleurl",ruleUrl.get(tem));
                Log.v("nima",ruleUrl.get(tem));
                bundle.putString("title",kind.get(tem));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }
    public static class ViewHolder{
        ImageView imageView;
        TextView textView;
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

        kind.add("建筑软件");
        kind.add("结构软件");
        kind.add("水利软件");
        kind.add("园林软件");
        kind.add("给排水软件");
        kind.add("暖通软件");
        kind.add("环保软件");
        kind.add("电气软件");

        ruleUrl.add("http://www.civilcn.com/jianzhu/jzrj/");
        ruleUrl.add("http://www.civilcn.com/jiegou/jgrj/");
        ruleUrl.add("http://www.civilcn.com/shuili/slrj/");
        ruleUrl.add("http://yuanlin.civilcn.com/ruanjian");
        ruleUrl.add("http://gps.civilcn.com/gpsrj/");
        ruleUrl.add("http://www.civilcn.com/nuantong/ntrj/");
        ruleUrl.add("http://huanbao.civilcn.com/hbrj/");
        ruleUrl.add("http://www.civilcn.com/dianqi/dqrj/");

    }
}
