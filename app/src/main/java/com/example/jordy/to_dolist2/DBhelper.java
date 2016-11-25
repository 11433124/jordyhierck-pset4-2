package com.example.jordy.to_dolist2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jordy on 22-11-2016.
 */

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "todo";

    String todo_id = "task";

    // constructor
    public DBhelper (Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + todo_id + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    // onUpgrade
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int I, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }

    // create
    public void create( String todo_element ){
        SQLiteDatabase db  = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(todo_id, todo_element);
        db.insert(TABLE, null, values);
        db.close();
    }

    // read
    public Cursor read() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = new String[] { "_id", "todo_id" };
        Cursor cursor = db.rawQuery("SELECT  * FROM todo", null);
        //Cursor cursor = db.query(TABLE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }
//    public ArrayList<HashMap<String, String>> read () {
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "SELECT _id , " + todo_id + " FROM " + TABLE;
//        ArrayList<HashMap<String, String>> todolist = new ArrayList<>();
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                HashMap<String, String> tododata = new HashMap<>();
//                tododata.put("id", cursor.getString(cursor.getColumnIndex("_id")));
//                tododata.put("task", cursor.getString(cursor.getColumnIndex(todo_id)));
//                todolist.add(tododata);
//            }
//            while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return todolist;
//    }

    //update
    public void update( String todo_element) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(todo_id, todo_element);
        db.update(TABLE, values, " _id = ? ", new String[] {String.valueOf(todo_id)});
        db.close();
    }


    //delete
    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, " _id = ? ", new String[] {String.valueOf(id)});
        db.close();
    }

}
