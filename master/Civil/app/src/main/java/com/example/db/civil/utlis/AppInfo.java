package com.example.db.civil.utlis;

import android.graphics.drawable.Drawable;

/**
 * Created by db on 4/15/15.
 */
public class AppInfo {

    private String		AppPkgName;
    private String		AppLauncherClassName;
    private String		AppName;
    private Drawable    AppIcon;

    public AppInfo() {
        super();
    }

    public AppInfo(String appPkgName, String appLauncherClassName) {
        super();
        AppPkgName = appPkgName;
        AppLauncherClassName = appLauncherClassName;
    }

    public String getAppPkgName() {
        return AppPkgName;
    }

    public void setAppPkgName(String appPkgName) {
        AppPkgName = appPkgName;
    }

    public String getAppLauncherClassName() {
        return AppLauncherClassName;
    }

    public void setAppLauncherClassName(String appLauncherClassName) {
        AppLauncherClassName = appLauncherClassName;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public Drawable getAppIcon() {
        return AppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        AppIcon = appIcon;
    }

}
