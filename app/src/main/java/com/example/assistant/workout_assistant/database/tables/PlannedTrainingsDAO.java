package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class PlannedTrainingsDAO extends DAO {

    public static final String TABLE_PLANNED_TRAININGS = "planned_trainings";

    //COLUMNS
    public static final String TRAINING_ID = "training_id";
    public static final String NOTIFICATION_BEFORE = "notification_before_id";
    public static final String NOTIFICATION_NOW = "notification_now_id";
    public static final String DATE = "date";

    public static final String CREATE_QUERY = CREATE + TABLE_PLANNED_TRAININGS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + TRAINING_ID + " TEXT, "
            + DATE + " TEXT, "
            + NOTIFICATION_BEFORE + " INTEGER, "
            + NOTIFICATION_NOW + " INTEGER, "
            + "FOREIGN KEY (" + TRAINING_ID +") REFERENCES trainings(" + KEY_ID + ") "+ DELETE_CASCADE +")";

    public static final String DELETE_QUERY = DELETE + TABLE_PLANNED_TRAININGS;

    public PlannedTrainingsDAO(Context context) {
        super(context);
    }

    public boolean insertPlannedTraining(String trainingId, String date, int notIdBefore, int notIdNow){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = fillValues(trainingId, date, notIdBefore, notIdNow);

        return db.insert(TABLE_PLANNED_TRAININGS, null, values) != -1;
    }

    private ContentValues fillValues(String trainingId, String date, int notIdBefore, int notIdNow) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRAINING_ID, trainingId);
        contentValues.put(DATE, date);
        contentValues.put(NOTIFICATION_BEFORE, notIdBefore);
        contentValues.put(NOTIFICATION_NOW, notIdNow);
        return contentValues;
    }


    public List<Integer> getAllNotificationsForTraining(String trainingId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<Integer> notificationsList = new ArrayList<>();

        String[] columns = { NOTIFICATION_BEFORE, NOTIFICATION_NOW};

        String where = TRAINING_ID + "=\"" + trainingId + "\"";
        Cursor cursor = db.query(TABLE_PLANNED_TRAININGS, columns, where, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                int beforeId = cursor.getInt(0);
                int nowId = cursor.getInt(1);

                if(beforeId >= 0) notificationsList.add(beforeId);

                notificationsList.add(nowId);

            }while (cursor.moveToNext());

            cursor.close();
        }

        return notificationsList;
    }
}
