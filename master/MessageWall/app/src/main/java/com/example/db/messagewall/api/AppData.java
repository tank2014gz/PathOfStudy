package com.example.db.messagewall.api;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.db.messagewall.adapter.WallAdapter;
import com.example.db.messagewall.bean.WallInfo;
import com.umeng.fb.push.FeedbackPush;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 7/15/15.
 */
public class AppData extends Application {


    public static final String KEY_CLIENT_ID = "client_id";
    static SharedPreferences preferences;

    public void onCreate(){

        super.onCreate();

        FeedbackPush.getInstance(this).init(false);

        /*
        注册APPID和APPKEY　
        注册消息处理类
         */
        AVOSCloud.initialize(this, "c24bthahdhtom81fheg54bkuxhfagmg9arplx9prunj71lzx"
                                 , "h1xcwrtpe07xkkttx9ueg3gz8v2rvjdl0ixoc6j387voa5le");
        AVIMMessageManager.registerDefaultMessageHandler(new MessageHandler(getApplicationContext()));

        /*
        启动错误Log日志
         */
        AVOSCloud.setDebugLogEnabled(true);

        /*
        初始化本地存储
         */
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static String getClientIdFromPre() {
        return preferences.getString(KEY_CLIENT_ID, "");
    }

    public static void setClientIdToPre(String id) {
        preferences.edit().putString(KEY_CLIENT_ID, id).apply();
    }

    public static AVIMClient getIMClient() {
        return AVIMClient.getInstance(getClientIdFromPre());
    }

}
