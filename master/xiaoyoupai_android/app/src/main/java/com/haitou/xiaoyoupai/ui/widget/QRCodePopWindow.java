package com.haitou.xiaoyoupai.ui.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.haitou.xiaoyoupai.R;

/**
 * Created by db on 9/3/15.
 */
public class QRCodePopWindow extends PopupWindow {

    public View view;

    public ImageView imageView;

    public QRCodePopWindow(Activity activity,View.OnClickListener onClickListener,int param1,int param2){
        super(activity);

        view = LayoutInflater.from(activity).inflate(R.layout.qrcode_popwindow,null);

        imageView = (ImageView)view.findViewById(R.id.qrcode);

        /*
        添加监听
         */
        if (onClickListener != null){
            imageView.setOnClickListener(onClickListener);
        }

        setContentView(view);
        setWidth(param1);
        setHeight(param2);

        setAnimationStyle(R.style.AnimTools);
        //透明背景
        setBackgroundDrawable(new ColorDrawable(0));
    }
}

