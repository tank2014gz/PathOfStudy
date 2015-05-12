package com.example.db.civil.utlis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.db.civil.R;
import com.example.db.civil.beans.Article;
import com.twotoasters.jazzylistview.JazzyHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 4/8/15.
 */
public class AppConstant {

    public static String USER_NAME="3025673709@qq.com";
    public static String NICHEN="......";
    public static boolean LOGIN_STATUS=false;
    public static int LISTVIEW_EFFECT= JazzyHelper.TILT;


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

    public static List<Article> list=new ArrayList<Article>();

    /**
     * 查询所有支持分享的应用信息
     *
     * @param context
     * @return
     */
    public static List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pManager = context.getPackageManager();
        mApps = pManager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        return mApps;
    }

    /**
     * 得到应用列表
     *
     * @return
     */
    public static List<AppInfo> getShareAppList(Context context) {
        List<AppInfo> shareAppInfos = new ArrayList<AppInfo>();
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = getShareApps(context);
        if (null == resolveInfos) {
            return null;
        }
        else {
            for (ResolveInfo resolveInfo : resolveInfos) {
                AppInfo appInfo = new AppInfo();
                appInfo.setAppPkgName(resolveInfo.activityInfo.packageName);
                appInfo.setAppLauncherClassName(resolveInfo.activityInfo.name);
                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));
                shareAppInfos.add(appInfo);
            }
        }
        return shareAppInfos;
    }



}
