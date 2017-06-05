package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RequirementsDAO extends DAO {

    public static final String TABLE_REQUIREMENTS = "requirements";

    public static String CREATE_QUERY = CREATE + TABLE_REQUIREMENTS +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT)";

    public static String DELETE_QUERY = DELETE + TABLE_REQUIREMENTS;

    protected RequirementsDAO(Context context) {
        super(context);
    }

    public long insertRequirement(String req) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = fillContent(req);

        return db.insert(TABLE_REQUIREMENTS, null, contentValues);
    }
    public long getId(String r) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =
                db.rawQuery("select 1 from " + TABLE_REQUIREMENTS + " where " + NAME + "=?",
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
