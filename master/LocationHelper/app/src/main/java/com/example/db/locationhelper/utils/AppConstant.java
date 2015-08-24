package com.example.db.locationhelper.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.example.db.locationhelper.R;
import com.example.db.locationhelper.view.SystemBarTintManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by db on 7/13/15.
 */
public class AppConstant {

    public static int QR_WIDTH=160;
    public static int QR_HEIGHT=160;

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
            tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_blue));
            tintManager.setStatusBarTintEnabled(true);
        }
    }
    public static void setStatus0(boolean on,Activity context){
                Window window = context.getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on){
            layoutParams.flags |=bits;
        }else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);

    }
}
