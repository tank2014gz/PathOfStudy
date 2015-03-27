package com.example.db.slor.utils;

import android.app.Application;

import com.example.db.slor.service.MyService;

/**
 * Created by db on 3/23/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyService.AVInit(this);
    }
}
