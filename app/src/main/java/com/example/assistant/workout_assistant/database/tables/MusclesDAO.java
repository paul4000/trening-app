package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MusclesDAO extends DAO {

    public static final String TABLE_MUSCLES = "muscles";

    public static String CREATE_QUERY = CREATE + TABLE_MUSCLES +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT)";

    public static String DELETE_QUERY = DELETE + TABLE_MUSCLES;

    protected MusclesDAO(Context context) {
        super(context);
    }

    public long getId(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =
                db.rawQuery("select 1 from " + TABLE_MUSCLES + " where " + NAME + "=?",
                        new String[]{ name });
        boolean exist = cursor.moveToFirst();
        cursor.close();
        return exist ? cursor.getInt(1) : -1;
    }

    public long insertMuscle(String muscle) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = fillContent(muscle);

        return db.insert(TABLE_MUSCLES, null, contentValues);
    }
    public ContentValues fillContent(String muscle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, muscle);
        return contentValues;
    }
}
