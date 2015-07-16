package com.example.db.messagewall.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.example.db.messagewall.view.SystemBarTintManager;
import com.support.android.designlibdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by db on 7/13/15.
 */
public class AppConstant {

    // 两个人之间的单聊
    public static int ConversationType_OneOne = 0;
    //群聊
    public static int ConversationType_Group = 1;

    public static SystemBarTintManager tintManager;

    public static void setStatus(boolean on,Activity context){
//        Window window = context.getWindow();
//        WindowManager.LayoutParams layoutParams=window.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on){
//            layoutParams.flags |=bits;
//        }else {
//            layoutParams.flags &= ~bits;
//        }
//        window.setAttributes(layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(context);
            tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar));
            tintManager.setStatusBarTintEnabled(true);
        }
    }
    /**
     * 方法描述：把dp转换为px<br>
     * 创建时间：2013-4-28  下午2:17:38   创建人：李小冰
     * @return
     */
    private int calculateDpToPx(int padding_in_dp,Context context){
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (int) (padding_in_dp * scale + 0.5f);
    }
    /*
    获取当前的时间
     */
    public static String getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String year = str.substring(0,4);
        String month = str.substring(5,7);
        String day = str.substring(8,10);
        return year+"."+month+"."+day;
    }
    /*
    将long型的时间转化成年、月、日的形式
     */
    public static String convertTime(long date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        /*
        将传入的时间转化成标准格式
         */
        Date curDate = new Date(date);
        String str = formatter.format(curDate);
        String year = str.substring(0,4);
        String month = str.substring(5,7);
        String day = str.substring(8,10);
        return year+"."+month+"."+day;
    }
}
