package com.example.assistant.workout_assistant.database.tables;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assistant.workout_assistant.bo.SeriesBean;

import java.util.ArrayList;
import java.util.List;

public class SeriesDAO extends DAO {

    public static final String TABLE_SERIES = "series";

    //COLUMNS
    public static final String EXE_BEAN_ID = "exe_bean_id";

    public static String CREATE_QUERY = CREATE + TABLE_SERIES + "("
                + KEY_ID + " INTEGER PRIMARY_KEY, "
                + TIME + " INTEGER, "
                + QUANTITY + " INTEGER, "
                + LOAD + " INTEGER, "
                + EXE_BEAN_ID + " INTEGER, "
                + "FOREIGN KEY (" + EXE_BEAN_ID +") REFERENCES exercise_beans(" + KEY_ID + ") "+ DELETE_CASCADE +")";

    public static String DELETE_QUERY = DELETE + TABLE_SERIES;

    protected SeriesDAO(Context context) {
        super(context);
    }

    public boolean insertSeries(SeriesBean s, long beanId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = fillValues(s, beanId);

        return db.insert(TABLE_SERIES, null, contentValues) != -1;
    }

    public List<SeriesBean> getSeries(int beanId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<SeriesBean> seriesBeen = new ArrayList<>();

        String[] columns = {SeriesDAO.TIME, SeriesDAO.QUANTITY, SeriesDAO.LOAD};
        String where = SeriesDAO.EXE_BEAN_ID + "=" + beanId;

        Cursor cursor = db.query(SeriesDAO.TABLE_SERIES, columns, where, null, null, null, null);

        if(cursor.moveToFirst()){
            do {
                int time = cursor.getInt(0);
                int quantity = cursor.getInt(1);
                int load = cursor.getInt(2);

                seriesBeen.add(new SeriesBean(time, quantity, load, ""));


            }while (cursor.moveToNext());
            cursor.close();
        }

        return seriesBeen;
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
