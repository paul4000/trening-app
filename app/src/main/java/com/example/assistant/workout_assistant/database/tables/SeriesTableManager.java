package com.example.assistant.workout_assistant.database.tables;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.assistant.workout_assistant.exercises.SeriesBean;

public class SeriesTableManager extends TableManager {

    private static final String TABLE_SERIES = "series";

    //COLUMNS
    private static final String EXE_BEAN_ID = "exe_bean_id";

    @Override
    public void create(SQLiteDatabase db) {
        String q =  CREATE + TABLE_SERIES + "("
                + KEY_ID + " INTEGER PRIMARY_KEY, "
                + TIME + " INTEGER, "
                + QUANTITY + " INTEGER, "
                + LOAD + " INTEGER, "
                + EXE_BEAN_ID + " INTEGER, "
                + "FOREIGN KEY (" + EXE_BEAN_ID +") REFERENCES exercise_beans(" + KEY_ID + ") "+ DELETE_CASCADE +")";

        db.execSQL(q);
    }

    @Override
    public void delete(SQLiteDatabase db) {
        db.execSQL(DELETE + TABLE_SERIES);
    }

    @Override
    public String getTableName() {
        return TABLE_SERIES;
    }

    public ContentValues fillValues(SeriesBean s, long beanId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIME, s.getTime());
        contentValues.put(QUANTITY, s.getQuantity());
        contentValues.put(LOAD, s.getLoad());
        contentValues.put(EXE_BEAN_ID, beanId);
        return contentValues;
    }
}
