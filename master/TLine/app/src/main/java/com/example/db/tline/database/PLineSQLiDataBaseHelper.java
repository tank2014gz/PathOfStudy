package com.example.db.tline.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by db on 4/23/15.
 */
public class PLineSQLiDataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="com.pline.db3";
    public static final int DATABASE_VERSION=1;


    public static String CREATE_TEXTLINE="create table pictureline (_id integer primary key autoincrement,title varchar(200),content varchar(400),uri varchar(200),date varchar(100),location varchar(200) ) ";

    public PLineSQLiDataBaseHelper(Context context) {
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
