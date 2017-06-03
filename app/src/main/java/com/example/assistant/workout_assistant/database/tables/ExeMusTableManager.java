package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ExeMusTableManager extends TableManager {

    private static final String TABLE_EXE_MUSCLES = "exe_mus";

    //COLUMNS
    private static final String MUSCLE_ID = "muscle_id";

    @Override
    public void create(SQLiteDatabase db) {
        String q = CREATE + TABLE_EXE_MUSCLES + "("
                + KEY_ID + " INTEGER PRIMARY_KEY, "
                + EXERCISE_ID + " TEXT, "
                + MUSCLE_ID + " INTEGER, "
                + "FOREIGN KEY (" + EXERCISE_ID + ") REFERENCES exercises(" + KEY_ID +") " + DELETE_CASCADE + ", "
                + "FOREIGN KEY (" + MUSCLE_ID +") REFERENCES muscles(" + KEY_ID + ") "+ DELETE_CASCADE +")";

        db.execSQL(q);
    }

    @Override
    public void delete(SQLiteDatabase db) {
        db.execSQL(DELETE + TABLE_EXE_MUSCLES);
    }

    @Override
    public String getTableName() {
        return TABLE_EXE_MUSCLES;
    }

    public ContentValues fillContent(String id, long muscleId) {
        ContentValues c = new ContentValues();
        c.put(EXERCISE_ID, id);
        c.put(MUSCLE_ID,  muscleId);
        return c;
    }
}
