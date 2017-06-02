package com.example.assistant.workout_assistant.database.tables;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.assistant.workout_assistant.exercises.Training;

public class TrainingsTableManager extends TableManager {

    //NAME
    private static final String TABLE_TRAINING = "trainings";
    //COLUMNS
    private static final String AUTHOR = "author";
    private static final String UPDATE = "updated";

    @Override
    public void create(SQLiteDatabase db) {
        db.execSQL(CREATE + TABLE_TRAINING + "("
                + KEY_ID + " TEXT PRIMARY KEY, "
                + NAME + " TEXT, "
                + AUTHOR + " TEXT, "
                + UPDATE + " TEXT)");
    }

    @Override
    public void delete(SQLiteDatabase db) {
        db.execSQL(DELETE + TABLE_TRAINING);
    }

    @Override
    public String getTableName() {
        return TABLE_TRAINING;
    }

    public ContentValues fillValues(Training training) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, training.get_id());
        contentValues.put(NAME, training.getName());
        contentValues.put(AUTHOR, training.getAuthor());
        contentValues.put(UPDATE, training.getUpdated());

        return contentValues;

    }
}
