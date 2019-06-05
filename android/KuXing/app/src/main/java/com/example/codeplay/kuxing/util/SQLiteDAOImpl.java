package com.example.codeplay.kuxing.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static android.content.Context.*;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class SQLiteDAOImpl {

    public SQLiteDAOImpl(){}

    private SQLiteDatabase db;

    public void createDB() {
        db = openOrCreateDatabase("kuxing.db", null);
        db.execSQL("DROP TABLE IF EXISTS person");
        db.execSQL("CREATE TABLE user (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, token VARCHAR)");
    }

    public void insertDB(String username, String token) {
        db.execSQL("INSERT INTO user VALUES (NULL, ?, ?)", new String[]{username, token});
    }

    public int queryDB(String username) {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE name = ?", new String[]{username});
        return cursor.getColumnCount();
////实例化一个对象
//        ArrayList<Map<String,Object>> list = new ArrayList<>();
////循环取值
//        while (cursor.moveToNext()){
////获取数据
//            String name = cursor.getString(1); //列按照从0开始算，1指的是name列
//            String name = cursor.getString(cursor.getColumnIndex("name"));//或者可以这样写
////实例化map对象
//            Map<String,Object> map = new HashMap<>();
////添加值
//            map.put("name",name);
////将map对象添加list中
//            list.add(map);
    }

    public void updateDB(String username, String token) {
        db.execSQL("UPDATE user SET token = ? WHERE username = ?", new Object[]{username, token});
    }

    public void deleteDB(String username) {
        db.execSQL("DELETE FROM user WHERE username = ?", new Object[]{username});
    }

    public void closeDB() {
        db.close();
    }

}
