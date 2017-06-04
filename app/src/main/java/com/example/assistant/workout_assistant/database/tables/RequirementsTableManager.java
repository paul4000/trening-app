package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RequirementsTableManager extends TableManager {

    public static final String TABLE_REQUIREMENTS = "requirements";

    @Override
    public void create(SQLiteDatabase db) {
        String q =  CREATE + TABLE_REQUIREMENTS +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT)";
        db.execSQL(q);
    }

    @Override
    public void delete(SQLiteDatabase db) {
        db.execSQL(DELETE + TABLE_REQUIREMENTS);
    }

    @Override
    public String getTableName() {
        return TABLE_REQUIREMENTS;
    }

    public long getId(SQLiteDatabase readableDatabase, String r) {
        Cursor cursor =
                readableDatabase.rawQuery("select 1 from " + TABLE_REQUIREMENTS + " where " + NAME + "=?",
                        new String[]{ r });
        boolean exist = cursor.moveToFirst();
        cursor.close();
        return exist ? cursor.getInt(1) : -1;
    }

    public ContentValues fillContent(String req) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, req);
        return contentValues;
    }

}
