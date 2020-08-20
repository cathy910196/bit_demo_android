package com.kouxuan.bit_demo_android.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KouxuanNB on 2016/10/19.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    private final static int _DBVersion = 1; //<-- 版本
    private final static String _DBName = "db1.db";	//<-- db name
    private final static String _TableName = "table01"; //<-- table name
    public MyDBHelper(Context context) {
        super(context, _DBName, null, _DBVersion);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "_bed_numbers TEXT, " +
                "_patient_names TEXT, " +
                "_bit_components TEXT, " +
                "_doctor_name TEXT" +
                ")";
        db.execSQL(SQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        final String SQL = "DROP TABLE " + _TableName;
        db.execSQL(SQL);
    }



}
