package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExeMusDAO extends DAO {


    public  static final String TABLE_EXE_MUSCLES = "exe_mus";

    //COLUMNS
    public static final String MUSCLE_ID = "muscle_id";

    public static String CREATE_QUERY = CREATE + TABLE_EXE_MUSCLES + "("
                + KEY_ID + " INTEGER PRIMARY_KEY, "
                + EXERCISE_ID + " TEXT, "
                + MUSCLE_ID + " INTEGER, "
                + "FOREIGN KEY (" + EXERCISE_ID + ") REFERENCES exercises(" + KEY_ID +") " + DELETE_CASCADE + ", "
                + "FOREIGN KEY (" + MUSCLE_ID +") REFERENCES muscles(" + KEY_ID + ") "+ DELETE_CASCADE +")";

    public static String DELETE_QUERY = DELETE + TABLE_EXE_MUSCLES;

    public static String JOIN_EXERCISES_MUSCLES_QUERY = "select " + ExerciseDAO.TABLE_EXERCISE+ "."+ ExerciseDAO.KEY_ID + " as exercise_id, "
            + MusclesDAO.TABLE_MUSCLES + "." + MusclesDAO.NAME + " as muscle"+
            " from " + ExeMusDAO.TABLE_EXE_MUSCLES
            + " inner join " + ExerciseDAO.TABLE_EXERCISE + " on " +
            ExerciseDAO.TABLE_EXERCISE+"."+ ExerciseDAO.KEY_ID + "=" + ExeMusDAO.TABLE_EXE_MUSCLES + "."+ ExeMusDAO.EXERCISE_ID
            + " inner join " + MusclesDAO.TABLE_MUSCLES + " on " +
            MusclesDAO.TABLE_MUSCLES+"."+ MusclesDAO.KEY_ID + "=" + ExeMusDAO.TABLE_EXE_MUSCLES + "."+ ExeMusDAO.MUSCLE_ID;

    protected ExeMusDAO(Context context) {
        super(context);
    }

    public boolean insertExeMusl(String id, long muscleId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = fillContent(id, muscleId);

        return db.insert(TABLE_EXE_MUSCLES, null, contentValues) != -1;
    }

    public ContentValues fillContent(String id, long muscleId) {
        ContentValues c = new ContentValues();
        c.put(EXERCISE_ID, id);
        c.put(MUSCLE_ID,  muscleId);
        return c;
    }

    public List<String> getMusclesForExercise(String exerciseId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> muscles = new ArrayList<>();

        Cursor cursorMuscles = db.rawQuery(JOIN_EXERCISES_MUSCLES_QUERY + " where exercise_id =?", new String[] { exerciseId });

        if(cursorMuscles.moveToFirst()){
            do{
                muscles.add(cursorMuscles.getString(1));
            }while (cursorMuscles.moveToNext());
            cursorMuscles.close();
        }

        return muscles;
    }

}
