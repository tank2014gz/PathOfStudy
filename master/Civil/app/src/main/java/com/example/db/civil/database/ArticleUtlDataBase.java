package com.example.db.civil.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by db on 4/8/15.
 */
public class ArticleUtlDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="com.civil1.db";
    public static final int DATABASE_VERSION=1;


    public static String CREATE_ARTICLE="create table articleurl (_id integer primary key autoincrement,url varchar(100),name varchar(100) ) ";

    public ArticleUtlDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ARTICLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
