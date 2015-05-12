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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 5/12/15.
 */
public class FormatAdapter extends BaseAdapter {
    public Context context;

    public List<Drawable> list=new ArrayList<Drawable>();

    public List<String> kind=new ArrayList<String>();

    public List<String> ruleUrl=new ArrayList<String>();

    public FormatAdapter(Context context){
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
                intent.setClass(context, RuleDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("ruleurl",ruleUrl.get(tem));
                Log.v("nima", ruleUrl.get(tem));
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
        list.add(context.getResources().getDrawable(R.drawable.rule9));

        kind.add("建筑设计论坛");
        kind.add("建筑结构论坛");
        kind.add("施工技术论坛");
        kind.add("给排水工程论坛");
        kind.add("环保工程论坛");
        kind.add("园林绿化论坛");
        kind.add("水利工程论坛");
        kind.add("路桥工程论坛");

        ruleUrl.add("/bbs/forum.php?gid=44");
        ruleUrl.add("/bbs/forum.php?gid=32");
        ruleUrl.add("/bbs/forum.php?gid=2344");
        ruleUrl.add("/bbs/forum.php?gid=3");
        ruleUrl.add("/bbs/forum.php?gid=77");
        ruleUrl.add("/bbs/forum.php?gid=54");
        ruleUrl.add("/bbs/forum.php?gid=67");
        ruleUrl.add("/bbs/forum.php?gid=2176");

    }

}
