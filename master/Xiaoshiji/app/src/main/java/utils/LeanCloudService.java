package utils;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beans.BringMealInfo;
import beans.CommitInfo;
import beans.DishMenuInfo;
import beans.FoundInfo;
import beans.LostInfo;

/**
 * Created by db on 6/2/15.
 */
public class LeanCloudService {

    public static String appId = "d02j5d4ht58yl8s93o4x5mwtslpl0awtjoigrwfaazdwh0m5";
    public static String appKey = "edkhzmoop8ekvimzpip2l0t5vgsozgeuttz2f39sjqnhd105";

    public static void AVInit(Context context){
        AVOSCloud.initialize(context, appId, appKey);
        // 启用崩溃错误报告
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(context, true);
        // 注册子类
        AVObject.registerSubclass(BringMealInfo.class);
        AVObject.registerSubclass(LostInfo.class);
        AVObject.registerSubclass(FoundInfo.class);
        AVObject.registerSubclass(CommitInfo.class);
        AVObject.registerSubclass(DishMenuInfo.class);
    }
    /*
    后台查找BringMealInfo对象
     */
    public static ArrayList<BringMealInfo> findBringMealInfos(){

        AVQuery<BringMealInfo> query = AVQuery.getQuery(BringMealInfo.class);
        query.orderByAscending("updateAt");
        query.limit(1000);
        List<BringMealInfo> bringMealInfoList = new ArrayList<BringMealInfo>();
        try {
            bringMealInfoList = query.find();
        }catch (AVException e){
            Log.e("tag", "Query todos failed.", e);
        }
        return (ArrayList<BringMealInfo>)bringMealInfoList;
    }
    /*
    后台查找LostInfo对象
     */
    public static ArrayList<LostInfo> findLostInfos(){

        AVQuery<LostInfo> query = AVQuery.getQuery(LostInfo.class);
        query.orderByAscending("updateAt");
        query.limit(1000);
        List<LostInfo> lostInfos = new ArrayList<LostInfo>();
        try {
            lostInfos = query.find();
        }catch (AVException e){
            Log.e("tag1", "Query todos failed.", e);
        }
        return (ArrayList<LostInfo>)lostInfos;
    }
    /*
    后台查找FoundInfo对象
     */
    public static ArrayList<FoundInfo> findFoundInfos(){

        AVQuery<FoundInfo> query = AVQuery.getQuery(FoundInfo.class);
        query.orderByAscending("updateAt");
        query.limit(1000);
        List<FoundInfo> foundInfos = new ArrayList<FoundInfo>();
        try {
            foundInfos = query.find();
        }catch (AVException e){
            Log.e("tag1", "Query todos failed.", e);
        }
        return (ArrayList<FoundInfo>)foundInfos;
    }
    /*
    根据条件查找
     */
    public static ArrayList<LostInfo> findLostInfosByRqs(){

        AVQuery<LostInfo> query = AVQuery.getQuery(LostInfo.class);
        List<LostInfo> lostInfos = new ArrayList<LostInfo>();
        if (AVUser.getCurrentUser()!=null){
            query.whereEqualTo("lostcontact", AVUser.getCurrentUser().getUsername());
            query.orderByAscending("updateAt");
            query.limit(1000);
            try {
                lostInfos = query.find();
            }catch (AVException e){
                Log.e("tag1", "Query todos failed.", e);
            }
        }else {
            Log.v("jb","null");
        }
        return (ArrayList<LostInfo>)lostInfos;
    }
    /*
    根据条件查找
     */
    public static ArrayList<FoundInfo> findFoundInfosByRqs(){

        AVQuery<FoundInfo> query = AVQuery.getQuery(FoundInfo.class);
        List<FoundInfo> foundInfos = new ArrayList<FoundInfo>();
        if (AVUser.getCurrentUser()!=null){
            query.whereEqualTo("foundcontact", AVUser.getCurrentUser().getUsername());
            query.orderByAscending("updateAt");
            query.limit(1000);
            try {
                foundInfos = query.find();
            }catch (AVException e){
                Log.e("tag1", "Query todos failed.", e);
            }
        }else {
            Log.v("jb","null");
        }
        return (ArrayList<FoundInfo>)foundInfos;
    }
    /*
    根据条件查找
     */
    public static ArrayList<BringMealInfo> findBringMealInfosByRqs(){

        AVQuery<BringMealInfo> query = AVQuery.getQuery(BringMealInfo.class);
        List<BringMealInfo> bringMealInfoList = new ArrayList<BringMealInfo>();
        if (AVUser.getCurrentUser()!=null){
            query.whereEqualTo("contacttype", AVUser.getCurrentUser().getUsername());
            query.orderByAscending("updateAt");
            query.limit(1000);
            try {
                bringMealInfoList = query.find();
            }catch (AVException e){
                Log.e("tag", "Query todos failed.", e);
            }
        }else {
            Log.v("jb","null");
        }

        return (ArrayList<BringMealInfo>)bringMealInfoList;
    }
    /*
    在后台获取评论
     */
    public static ArrayList<CommitInfo> findCommitInfos(){

        AVQuery<CommitInfo> query = AVQuery.getQuery(CommitInfo.class);
            /*
            db 如果是查找某一个食堂的所有评论，就加一个条件
            query.whereEqualTo("diningroomname", "食堂名称");
             */
            query.orderByAscending("updateAt");
            query.limit(1000);
        List<CommitInfo> commitInfoList = new ArrayList<CommitInfo>();
            try {
                commitInfoList = query.find();
            }catch (AVException e){
                Log.e("tag", "Query todos failed.", e);
            }

        return (ArrayList<CommitInfo>)commitInfoList;
    }

}
