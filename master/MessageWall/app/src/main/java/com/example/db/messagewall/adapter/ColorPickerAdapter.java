package com.example.db.messagewall.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 8/1/15.
 */
public class ColorPickerAdapter extends BaseAdapter{

    public Context context;
    public List<Integer> bkg = new ArrayList<Integer>();
    public List<String> name = new ArrayList<String>();
    public List<Integer> theme = new ArrayList<Integer>();

    public int themeId;

    /*
    来限定只能选择一个主题一次性
     */
    public boolean Flag = false;

    public ColorPickerAdapter(Context context){
        super();
        this.context = context;

        initList();
    }

    @Override
    public int getCount() {
        return bkg.size();
    }

    @Override
    public Object getItem(int position) {
        return bkg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.color_item,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView)convertView.findViewById(R.id.textview);
            viewHolder.linearLayout = (LinearLayout)convertView.findViewById(R.id.btn_share);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }



        viewHolder.textView.setTextColor(bkg.get(position));
        viewHolder.textView.setText(name.get(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.linearLayout.getVisibility()==ViewGroup.VISIBLE){
                    viewHolder.linearLayout.setVisibility(ViewGroup.GONE);
                    Flag = false;
                }else if (viewHolder.linearLayout.getVisibility()==ViewGroup.GONE&&Flag==false){
                    viewHolder.linearLayout.setVisibility(ViewGroup.VISIBLE);
                    setThemeId(theme.get(position));
                    Flag = true;
                }
            }
        });

        return convertView;
    }
    public static class ViewHolder{
        TextView textView;
        LinearLayout linearLayout;
    }

    public void initList(){

        bkg.add(R.color.actionbar);
        bkg.add(R.color.actionbar_blue);
        bkg.add(R.color.actionbar_wumai);
        bkg.add(R.color.actionbar_chinared);
        bkg.add(R.color.actionbar_gdblack);
        bkg.add(R.color.actionbar_doubangreen);
        bkg.add(R.color.actionbar_xiaomiorigin);
        bkg.add(R.color.actionbar_dansuiyellow);
        bkg.add(R.color.actionbar_naocanpink);
        bkg.add(R.color.actionbar_mensaopurple);

        name.add("留言墙绿");
        name.add("天空蓝");
        name.add("雾霾灰");
        name.add("中国红");
        name.add("高端黑");
        name.add("豆瓣青");
        name.add("小米橙");
        name.add("蛋碎黄");
        name.add("脑残粉");
        name.add("闷骚紫");

        theme.add(R.style.nLiveo_Theme_DarkActionBar);
        theme.add(R.style.nLiveo_Theme_BlueActionBar);
        theme.add(R.style.nLiveo_Theme_WumaiActionBar);
        theme.add(R.style.nLiveo_Theme_ChinaredActionBar);
        theme.add(R.style.AppTheme_GdblackActionBar);
        theme.add(R.style.nLiveo_Theme_DoubangreenActionBar);
        theme.add(R.style.nLiveo_Theme_XiaomigreenActionBar);
        theme.add(R.style.nLiveo_Theme_DansuiyellowActionBar);
        theme.add(R.style.nLiveo_Theme_NaocanpinkActionBar);
        theme.add(R.style.nLiveo_Theme_MensaopurpleActionBar);

    }

    public void setThemeId(int themeId){
        this.themeId = themeId;
    }
    public int getThemeId(){
        return this.themeId;
    }
}
