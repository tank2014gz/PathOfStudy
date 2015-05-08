package com.example.db.tline.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by db on 4/22/15.
 */
public class TLineSQLiDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="com.tline.db01";
    public static final int DATABASE_VERSION=1;


    public static String CREATE_TEXTLINE="create table textline (_id integer primary key autoincrement,title varchar(200),content varchar(400),date varchar(100),location varchar(200) ) ";

    public TLineSQLiDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TEXTLINE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
