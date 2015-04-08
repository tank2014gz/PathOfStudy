package com.example.db.civil.utlis;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by db on 4/8/15.
 */
public class AppConstant {


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
