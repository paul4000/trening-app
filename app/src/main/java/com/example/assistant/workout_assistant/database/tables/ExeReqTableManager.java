package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ExeReqTableManager extends TableManager {

    private static final String TABLE_EXE_REQ = "exe_req";

    //COLUMNS
    private static final String REQ_ID = "requirement_id";

    @Override
    public void create(SQLiteDatabase db) {
        String q = CREATE + TABLE_EXE_REQ + " ("
                + KEY_ID + " INTEGER PRIMARY_KEY, "
                + EXERCISE_ID + " TEXT, "
                + REQ_ID + " INTEGER, "
                + "FOREIGN KEY (" + EXERCISE_ID + ") REFERENCES exercises(" + KEY_ID +") " + DELETE_CASCADE + ", "
                + "FOREIGN KEY (" + REQ_ID +") REFERENCES requirements(" + KEY_ID + ") " + DELETE_CASCADE+ ")";
        db.execSQL(q);
    }

    @Override
    public void delete(SQLiteDatabase db) {
        db.execSQL(DELETE + TABLE_EXE_REQ);
    }

    @Override
    public String getTableName() {
        return TABLE_EXE_REQ;
    }

    public ContentValues fillContent(String id, long reqId) {
        ContentValues c = new ContentValues();
        c.put(EXERCISE_ID, id);
        c.put(REQ_ID, reqId);
        return c;
    }
}
