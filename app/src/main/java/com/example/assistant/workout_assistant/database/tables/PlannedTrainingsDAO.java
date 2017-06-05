package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


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
            + DATE + " DATETIME, "
            + NOTIFICATION_BEFORE + " INTEGER, "
            + NOTIFICATION_NOW + " INTEGER, "
            + "FOREIGN KEY (" + TRAINING_ID +") REFERENCES trainings(" + KEY_ID + ") "+ DELETE_CASCADE +")";

    public static final String DELETE_QUERY = DELETE + TABLE_PLANNED_TRAININGS;

    protected PlannedTrainingsDAO(Context context) {
        super(context);
    }

    public boolean insertPlannedTraining(String trainingId, long notIdBefore, long notIdNow){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = fillValues(trainingId, notIdBefore, notIdNow);

        return db.insert(TABLE_PLANNED_TRAININGS, null, values) != -1;
    }

    private ContentValues fillValues(String trainingId, long notIdBefore, long notIdNow) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRAINING_ID, trainingId);
        contentValues.put(NOTIFICATION_BEFORE, notIdBefore);
        contentValues.put(NOTIFICATION_NOW, notIdNow);
        return contentValues;
    }


}
