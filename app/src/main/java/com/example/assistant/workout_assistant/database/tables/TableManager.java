package com.example.assistant.workout_assistant.database.tables;


import android.database.sqlite.SQLiteDatabase;

public abstract class TableManager {

    protected static final String CREATE = "CREATE TABLE ";
    protected static final String DELETE = "DROP TABLE IF EXIST ";
    protected static final String DELETE_CASCADE = "ON DELETE CASCADE";


    //COMMON COLUMN NAMES
    protected static final String KEY_ID = "id";
    protected static final String NAME = "name";
    protected static final String TIME = "time";
    protected static final String LOAD = "load";
    protected static final String QUANTITY = "quantity";
    protected static final String EXERCISE_ID = "exercise_id";

    public abstract void create(SQLiteDatabase db);
    public abstract void delete(SQLiteDatabase db);
    public abstract String getTableName();
    public String getKeyColumnName(){
        return KEY_ID;
    }

}
