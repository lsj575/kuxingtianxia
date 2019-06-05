package com.example.codeplay.kuxing.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //类没有实例化,是不能用作父类构造器的参数,必须声明为静态

    private static final String name = "kuxingapp"; //数据库名称

    private static final int version = 1; //数据库版本

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
    }

    public static DatabaseHelper getInstance(Context context){
        if(instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (username VARCHAR, token VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
