package com.example.db.civil.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.db.civil.R;
import com.example.db.civil.beans.Todo;

import org.markdown4j.Markdown4jProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by db on 3/21/15.
 */
public class MyAdapter extends BaseAdapter {

    public TextView textView;
    public Context context;

    public EditText editText;

    public WebSettings webSettings;

    public String html;

    public List<Todo> todos;

    public String str;

    public MyAdapter(Context context,List<Todo> todos){
        super();
        this.context=context;
        this.todos=todos;
    }

    @Override
    public int getCount() {
        return todos != null ? todos.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        if (todos!=null){
            return todos.get(i);
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.boardcard, null);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            str = formatter.format(curDate);

            holder = new ViewHolder();

            holder.date=(TextView)convertView.findViewById(R.id.data_tv);
            holder.content=(TextView)convertView.findViewById(R.id.content_tv);

            convertView.setTag(holder);



        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        final Todo todo = todos.get(i);

        if (todo != null)
            try {
                holder.date.setText(str);



            }catch (Exception e){
                e.printStackTrace();
            }

        return convertView;

    }
    public static class ViewHolder
    {

        TextView date,content;
    }
}
