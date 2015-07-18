package com.example.db.messagewall.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.example.db.messagewall.api.AppData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 7/18/15.
 */
public class ShareData {



    /**
     * 判断是否安装腾讯、新浪等指定的分享应用
     * @param packageName 应用的包名
     */
    public static boolean checkInstallation(String packageName,Context context){

        try {
            context.getPackageManager().getPackageInfo(packageName
                                        ,PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /*
    分享到指定的应用
     */
    public static void filterShare(String CONVERSATION_ID,Context context){

        AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,0);
        if(list!=null){
            List<Intent> targetIntents = new ArrayList<Intent>();
            for (ResolveInfo resolveInfo : list){
                Intent temp = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (activityInfo.packageName.contains("com.tencent.mm")
                        ||activityInfo.packageName.contains("com.sina.weibo")
                        ||activityInfo.packageName.contains("com.qzone")
                        ||activityInfo.packageName.contains("com.tencent.mobileqq")
                        ||activityInfo.packageName.contains("com.android.mms")){
                    temp.setPackage(activityInfo.packageName);
                    targetIntents.add(temp);

                }else {
                    continue;
                }
            }
            Intent chooserIntent = Intent.createChooser(targetIntents.remove(0),"选择分享的应用");
            if (chooserIntent==null){
                return;
            }

            try{
                chooserIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(avimConversation
                        .getAttribute("link_url")
                        .toString()));
                context.startActivity(chooserIntent);
            }catch(android.content.ActivityNotFoundException ex){
                Log.v("share.error",ex.getMessage());
            }
        }else {

        }
    }

}
