package com.example.db.messagewall.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.support.android.designlibdemo.R;

/**
 * Created by db on 8/1/15.
 */
public class ThemeHelper {

    public static final String KEY_THEME = "com.example.db.messagewalltheme";

    private static SharedPreferences.Editor mEditor = null;
    private static SharedPreferences mdPreferences = null;

    public ThemeHelper(Context context){

    }
    private static SharedPreferences.Editor getEditor(Context paramContext) {
        if (mEditor == null)
            mEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
        return mEditor;
    }

    private static SharedPreferences getSharedPreferences(Context paramContext) {
        if (mdPreferences == null)
            mdPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
        return mdPreferences;
    }
    public static int getTheme(Context context){
        return ThemeHelper.getSharedPreferences(context).getInt(KEY_THEME, R.style.nLiveo_Theme_WumaiActionBar);
    }

    public static void setTheme(Context context, int theme){
        getEditor(context).putInt(KEY_THEME, theme).commit();
    }
}
