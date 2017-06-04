package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MusclesTableManager extends TableManager {

    public static final String TABLE_MUSCLES = "muscles";

    @Override
    public void create(SQLiteDatabase db) {
        String q =  CREATE + TABLE_MUSCLES +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT)";
        db.execSQL(q);
    }

    @Override
    public void delete(SQLiteDatabase db) {
        db.execSQL(DELETE + TABLE_MUSCLES);
    }

    public long getId(SQLiteDatabase db, String name){
        Cursor cursor =
                db.rawQuery("select 1 from " + TABLE_MUSCLES + " where " + NAME + "=?",
                        new String[]{ name });
        boolean exist = cursor.moveToFirst();
        cursor.close();
        return exist ? cursor.getInt(1) : -1;
    }

    @Override
    public String getTableName() {
        return TABLE_MUSCLES;
    }

    public ContentValues fillContent(String muscle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, muscle);
        return contentValues;
    }
}
