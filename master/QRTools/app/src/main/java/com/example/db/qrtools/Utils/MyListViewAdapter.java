package com.example.db.qrtools.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.qrtools.R;
import com.example.db.qrtools.Beans.getTotalInfo;

import java.io.File;
import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by db on 3/4/15.
 */
public class MyListViewAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<getTotalInfo> getTotalInfos;
    public ImageView imageView;
    public Button details;
    public TextView textView;
    public Bitmap[] bitmaps;
    public View tempView;
    public File file=null;
    public String content=null;
    public String name=null;


    public MyListViewAdapter(Context context){
        super();
        this.context=context;

        getTotalInfos=Utils.getTotalInfos(context);
        bitmaps=Utils.getBitmap();
    }
    @Override
    public int getCount() {
        return getTotalInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View RootView=layoutInflater.inflate(R.layout.expandlistview_child_item,null);

        final int count=i;
        imageView=(ImageView)RootView.findViewById(R.id.picture);
        details=(Button)RootView.findViewById(R.id.details);
        imageView.setImageBitmap(bitmaps[i]);

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempView=LayoutInflater.from(context).inflate(R.layout.details_item,null);
                textView=(TextView)tempView.findViewById(R.id.show_details);

                file=new File(getTotalInfos.get(count).getPath());
                name=file.getName();
                content=null;

                final MaterialDialog materialDialog=new MaterialDialog(context);
                //区别类型;
                switch (name.substring(0,2)){
                    case "01":
                        content="文件名:"+file.getName().substring(3,file.getName().length()-4)+"\n"+"类型:"+"自定义二维码"+"\n"+"时间:"+getTotalInfos.get(count).getTime();
                        textView.setText(content);
                        break;
                    case "02":

                        break;
                    case "03":
                        content="文件名:"+file.getName().substring(3,file.getName().length()-4)+"\n"+"类型:"+"名片二维码"+"\n"+"时间:"+getTotalInfos.get(count).getTime();
                        textView.setText(content);
                        break;
                    case "04":
                        content="文件名:"+file.getName().substring(3,file.getName().length()-4)+"\n"+"类型:"+"邮件二维码"+"\n"+"时间:"+getTotalInfos.get(count).getTime();
                        textView.setText(content);
                        break;
                    case "05":
                        content="文件名:"+file.getName().substring(3,file.getName().length()-4)+"\n"+"类型:"+"短信二维码"+"\n"+"时间:"+getTotalInfos.get(count).getTime();
                        textView.setText(content);
                        break;
                    case "06":
                        content="文件名:"+file.getName().substring(3,file.getName().length()-4)+"\n"+"类型:"+"网址二维码"+"\n"+"时间:"+getTotalInfos.get(count).getTime();
                        textView.setText(content);
                        break;
                }
                materialDialog.setTitle("Details");
                materialDialog.setContentView(tempView);
                materialDialog.setPositiveButton("Ok",new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        materialDialog.dismiss();
                    }
                });
                materialDialog.setNegativeButton("Cancel",new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        materialDialog.dismiss();
                    }
                });

                materialDialog.show();
            }

        });
        return RootView;
    }

}
