package com.example.db.tline.api;

import android.app.Application;


/**
 * Created by db on 3/23/15.
 */
public class MyApplication extends Application {

    public static String BAIDU_KEY="3HNWYPKIZDMLgcsv5Z8QgDVC";

    @Override
    public void onCreate() {
        super.onCreate();
        MyService.AVInit(this);

    }

}
