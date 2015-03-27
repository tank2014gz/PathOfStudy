package com.example.db.slor.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.search.AVSearchQuery;
import com.example.db.slor.beans.Article;

import java.util.Collections;
import java.util.List;

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
        AVObject.registerSubclass(Article.class);
    }
    public static void fetchTodoById(String objectId,GetCallback<AVObject> getCallback){
        Article article=new Article();
        article.setObjectId(objectId);
        article.fetchInBackground(getCallback);
    }
    public static void createOrUpdateTodo(String title,String content,AVUser avUser,String date,boolean isOthersSee,boolean isOwnSee,SaveCallback saveCallback){
        final Article article=new Article();
        if (!TextUtils.isEmpty(title)) {
            article.setOwnSee(isOwnSee);

        }
        article.setTitle(title);
        article.setContent(content);
        article.setUser(avUser);
        article.setDate(date);
        article.setOthersSee(isOthersSee);
        article.setOwnSee(isOwnSee);
        article.saveInBackground(saveCallback);
    }
    public static List<Article> findTodos(){
        AVQuery<Article> query=AVQuery.getQuery(Article.class);
        query.orderByDescending("updateAt");
        query.limit(1000);
        try {
            return query.find();
        }catch (AVException e){
            Log.e("tag", "Query todos failed.", e);
            return Collections.emptyList();
        }
    }
    public static void searchQuery(String inputSearch){
        AVSearchQuery searchQuery = new AVSearchQuery(inputSearch);
        searchQuery.search();
    }

}
