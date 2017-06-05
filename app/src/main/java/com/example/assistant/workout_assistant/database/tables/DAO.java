package com.example.assistant.workout_assistant.database.tables;


import android.content.Context;

import com.example.assistant.workout_assistant.database.DBHelper;

public abstract class DAO {


     protected DBHelper dbHelper;

     static final String CREATE = "CREATE TABLE ";
     static final String DELETE = "DROP TABLE IF EXISTS ";
     static final String DELETE_CASCADE = "ON DELETE CASCADE";
     static final String SELECT_EXISTING = "select 1 from ";


    //COMMON COLUMN NAMES
    public static final String KEY_ID = "_id";
    public static final String NAME = "name";
    public static final String TIME = "time";
    public static final String LOAD = "load";
    public static final String QUANTITY = "quantity";
    public static final String EXERCISE_ID = "exercise_id";



    protected DAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public void close(){
        dbHelper.close();
    }


}
