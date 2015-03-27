package com.example.db.slor.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by db on 3/23/15.
 */
public class Utils {

    public static String USER_OBJECTED;



    public static void setStatus(boolean on,Activity context){
        Window window=context.getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        final int bits=WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on){
            layoutParams.flags |=bits;
        }else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

}
