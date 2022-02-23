package com.example.homepage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TASK_TABLE = "task_table";
    private static final String TASK_ID = "task_id";
    private static final String TASK_NAME = "task_name";

    private static final String DATE_TABLE = "date_table";
    private static final String DATE_ID = "date_id";
    private static final String DATE_NAME = "date_name";
    private static final String DATE_TEXT = "date_text";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "reminder.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryString = " CREATE TABLE " + TASK_TABLE + " (" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK_NAME + " TEXT " + ")";
        String queryString2 = " CREATE TABLE " + DATE_TABLE + " (" + DATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE_NAME + " TEXT," + DATE_TEXT + " TEXT " + " )";


        sqLiteDatabase.execSQL(queryString2);
        sqLiteDatabase.execSQL(queryString);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TASK_TABLE);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + DATE_TABLE);
        onCreate(sqLiteDatabase);

    }

    /**
     * Adding one task to to_do_task table
     * @param taskList
     */
    public boolean addOneTask(TaskListModel taskList){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TASK_NAME, taskList.getTaskName());
        long insert = sqLiteDatabase.insert(TASK_TABLE, null, contentValues);

        if (insert == 1){
            return true;
        }
        else
            return false;

    }
    /**
     * Deleting task in to_do_task table
     * @return
     * @param taskList
     */
    public boolean deleteOneTask(TaskListModel taskList){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String queryString = " DELETE FROM " + TASK_TABLE + " WHERE " + TASK_ID + " = " + taskList.getId();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst())
            return true;
        else
            return false;

    }

    /**
     * Reading all task in Task table in database
     * @return
     */
    public List<TaskListModel> taskLists(){


        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<TaskListModel> taskModels = new ArrayList<>();

        String queryString = " SELECT * FROM " + TASK_TABLE;
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String taskName = cursor.getString(1);

            TaskListModel taskList = new TaskListModel(id, taskName);
            taskModels.add(taskList);

        }
        return taskModels;
    }

    /**
     * Adding date into date_database
     */
    public boolean addOneTask(DateListModel dateListModel){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DATE_NAME, dateListModel.getDate_name());
        contentValues.put(DATE_TEXT, dateListModel.getDate_text());

        long insert = sqLiteDatabase.insert(DATE_TABLE, null, contentValues);

        if (insert == 1){
            return true;
        }
        else
            return false;

    }

    /**
     * Read all the date_text from Date Table
     * @return
     */
    public List<DateListModel> dateList(){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<DateListModel> dateListModels = new ArrayList<>();

        String queryString = " SELECT * FROM " + DATE_TABLE;
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String dateName = cursor.getString(1);
            String taskName = cursor.getString(2);


            DateListModel dateListModel = new DateListModel(id, dateName, taskName);
            dateListModels.add(dateListModel);

        }
        return dateListModels;

    }
}
