package com.example.db.tline.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by db on 5/1/15.
 */
public class PLineListSQLIDataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="com.plinelist.db2";
    public static final int DATABASE_VERSION=1;


    public static String CREATE_TEXTLINE="create table picturelinelist (_id integer primary key autoincrement,relationtitle varchar(200),relationcontent varchar(400),relationdate varchar(100),title varchar(200),content varchar(400),date varchar(100) ,url varchar(400)) ";

    public PLineListSQLIDataBaseHelper(Context context) {
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
