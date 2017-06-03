package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.assistant.workout_assistant.exercises.Training;

public class ExerciseBeanTableManager extends TableManager {

    private static final String TABLE_EXERCISE_BEAN = "exercise_beans";

    //COLUMNS
    private static final String TRAINING_ID = "training_id";

    @Override
    public void create(SQLiteDatabase db) {
        String q = CREATE + TABLE_EXERCISE_BEAN + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + EXERCISE_ID + " TEXT, "
                + TIME + " INTEGER, "
                + LOAD + " INTEGER, "
                + QUANTITY + " INTEGER, "
                + TRAINING_ID + " TEXT, "
                + "FOREIGN KEY (" + EXERCISE_ID + ") REFERENCES exercises(" + KEY_ID +") " + DELETE_CASCADE + ", "
                + "FOREIGN KEY (" + TRAINING_ID +") REFERENCES trainings(" + KEY_ID + ") "+ DELETE_CASCADE +")";
        db.execSQL(q);
    }

    @Override
    public void delete(SQLiteDatabase db) {

        db.execSQL(DELETE + TABLE_EXERCISE_BEAN);
    }

    @Override
    public String getTableName() {
        return TABLE_EXERCISE_BEAN;
    }

    public ContentValues fillValues(Training.ExercisesBean bean, String trainingId) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(EXERCISE_ID, bean.getExercise().get_id());
        contentValues.put(TIME, bean.isTime() ? 1 : 0);
        contentValues.put(LOAD, bean.isLoad() ? 1 : 0);
        contentValues.put(QUANTITY, bean.isQuantity() ? 1 : 0);
        contentValues.put(TRAINING_ID, trainingId);

        return contentValues;
    }
}
