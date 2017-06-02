package com.example.assistant.workout_assistant.database.tables;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.assistant.workout_assistant.exercises.Exercise;

public class ExerciseTableManager extends TableManager {

    private static final String TABLE_EXERCISE = "exercises";

    //COLUMNS
    private static final String DESCRIPTION = "description";
    private static final String PLACE = "place";


    @Override
    public void create(SQLiteDatabase db) {
        String q = CREATE + TABLE_EXERCISE + "("
                + KEY_ID + " TEXT PRIMARY KEY, "
                + NAME + " TEXT, "
                + PLACE + " TEXT, "
                + DESCRIPTION + " TEXT)";
        db.execSQL(q);
    }

    @Override
    public void delete(SQLiteDatabase db) {
        db.execSQL(DELETE + TABLE_EXERCISE);
    }

    @Override
    public String getTableName() {
        return TABLE_EXERCISE;
    }

    public ContentValues fillValues(Exercise exercise) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, exercise.get_id());
        contentValues.put(NAME, exercise.getName());
        contentValues.put(PLACE, exercise.getPlace());
        contentValues.put(DESCRIPTION, exercise.getDescription());
        return contentValues;

    }
}
