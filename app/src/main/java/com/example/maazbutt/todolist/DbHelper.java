package com.example.maazbutt.todolist;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by maazbutt on 1/24/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_Name = "EDMTDev";
    private static final int DB_VER = 1;
    private static final String DB_Table = "Task";
    public static final String DB_COLUM = "TaskName";

    public DbHelper(Context context){

        super(context,DB_Name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query= String.format("Create Table %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);",DB_Table,DB_COLUM);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query= String.format("DELETE TABLE IF EXISTS %s",DB_Table);
        db.execSQL(query);
        onCreate(db);

    }

    public void insertNewTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUM,task);
        db.insertWithOnConflict(DB_Table,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_Table,DB_COLUM + "= ?",new String[]{task});
        db.close();
    }


    public ArrayList<String>getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_Table,new String[]{DB_COLUM},null,null,null,null,null);
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUM);
            taskList.add(cursor.getString(index));

        }
        cursor.close();
        db.close();
        return taskList;


    }



}