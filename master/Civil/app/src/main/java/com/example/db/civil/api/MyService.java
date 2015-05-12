package com.example.db.civil.api;

import android.content.Context;
import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;


/**
 * Created by db on 3/23/15.
 */
public class MyService {
    public static String appId = "yoila15scgf45yzc9zsv818nmjwnbmuj3cnbpfcricus1fls";
    public static String appKey = "owylwhcmxf3p69c8dlxm8x157hshxiuqn5lwgjwddk23s9mp";
    public static void AVInit(Context context){
        AVOSCloud.initialize(context, appId, appKey);
        // 启用崩溃错误报告
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(context, true);
        // 注册子类
    }
}
