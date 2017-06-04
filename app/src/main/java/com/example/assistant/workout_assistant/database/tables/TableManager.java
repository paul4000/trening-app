package com.example.assistant.workout_assistant.database.tables;


import android.database.sqlite.SQLiteDatabase;

public abstract class TableManager {

     static final String CREATE = "CREATE TABLE ";
     static final String DELETE = "DROP TABLE IF EXISTS ";
     static final String DELETE_CASCADE = "ON DELETE CASCADE";


    //COMMON COLUMN NAMES
    public static final String KEY_ID = "_id";
    public static final String NAME = "name";
    public static final String TIME = "time";
    public static final String LOAD = "load";
    public static final String QUANTITY = "quantity";
    public static final String EXERCISE_ID = "exercise_id";

    public abstract void create(SQLiteDatabase db);
    public abstract void delete(SQLiteDatabase db);
    public abstract String getTableName();
    public String getKeyColumnName(){
        return KEY_ID;
    }

}
