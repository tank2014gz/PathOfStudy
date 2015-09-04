package com.haitou.xiaoyoupai.ui.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.haitou.xiaoyoupai.R;

/**
 * Created by db on 8/27/15.
 */
public class SelfPopWindow extends PopupWindow{

    public View view;
    public LinearLayout lin_activity,lin_people,lin_sign,lin_help;

    public SelfPopWindow(Activity activity,View.OnClickListener onClickListener,int param1,int param2){
        super(activity);

        view = LayoutInflater.from(activity).inflate(R.layout.popwindow_item,null);

        lin_activity = (LinearLayout)view.findViewById(R.id.activity_menu);
        lin_people = (LinearLayout)view.findViewById(R.id.people_menu);
        lin_sign = (LinearLayout)view.findViewById(R.id.sign_in_menu);
        lin_help = (LinearLayout)view.findViewById(R.id.help_menu);

        /*
        添加监听
         */
        if (onClickListener != null){
            lin_activity.setOnClickListener(onClickListener);
            lin_people.setOnClickListener(onClickListener);
            lin_sign.setOnClickListener(onClickListener);
            lin_help.setOnClickListener(onClickListener);
        }

        setContentView(view);
        setWidth(param1);
        setHeight(param2);

        setAnimationStyle(R.style.AnimTools);
        //透明背景
        setBackgroundDrawable(new ColorDrawable(0));
    }
}
