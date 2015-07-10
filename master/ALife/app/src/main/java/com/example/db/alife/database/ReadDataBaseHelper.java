package com.example.db.alife.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by db on 7/10/15.
 */
public class ReadDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="com.example.db.alife_read2";
    public static final int DATABASE_VERSION=1;


    public static String CREATE_ARTICLE="create table alife_read (_id integer primary key autoincrement,title varchar(100),tag varchar(100),description varchar(1000),picture varchar(200),url varchar(200) ) ";

    public ReadDataBaseHelper(Context context) {
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
