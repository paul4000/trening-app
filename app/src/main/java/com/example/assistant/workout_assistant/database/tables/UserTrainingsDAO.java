package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assistant.workout_assistant.bo.Training;

import java.util.ArrayList;
import java.util.List;


public class UserTrainingsDAO extends DAO {

    private TrainingsDAO trainingsDAO;

    public static final String TABLE_USER_TRAININGS = "user_trainings";

    //columns
    public static final String USER_ID = "user_id";
    public static final String TRAINING_ID = "training_id";

    public static String CREATE_QUERY  = CREATE + TABLE_USER_TRAININGS+ " ("
            + KEY_ID + " INTEGER PRIMARY_KEY, "
            + USER_ID + " TEXT, "
            + TRAINING_ID + " TEXT, "
            + "FOREIGN KEY (" + TRAINING_ID + ") REFERENCES trainings(" + KEY_ID +") " + DELETE_CASCADE + ")";

    public static String DELETE_QUERY  = DELETE + TABLE_USER_TRAININGS;

    public UserTrainingsDAO(Context context) {
        super(context);
        trainingsDAO = new TrainingsDAO(context);
    }

    public boolean hasTraining(String userId, String trainingId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor =
                db.rawQuery("select 1 from " + TABLE_USER_TRAININGS + " where " + TRAINING_ID + "=? AND "
                + USER_ID + "=?", new String[] {trainingId, userId});
        boolean has = cursor.moveToFirst();
        cursor.close();

        return has;
    }

    public boolean insertTraining(String userId, Training training){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if(!trainingsDAO.trainingExist(training.get_id())){
            if(!trainingsDAO.insertTraining(training)) return false;
        }
        ContentValues contentValues = fillValues(userId, training.get_id());

        return db.insert(TABLE_USER_TRAININGS, null, contentValues) != -1;
    }

    public boolean deleteTraining(String userId, String trainingId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delete = db
                .delete(TABLE_USER_TRAININGS, USER_ID + " = ? AND " + TRAINING_ID + " =?",
                        new String[]{ userId, trainingId});

        return delete == 1;

    }

    private ContentValues fillValues(String userId, String trainingId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, userId);
        contentValues.put(TRAINING_ID, trainingId);
        return contentValues;
    }

    public List<Training> getUserTrainings(String userId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Training> trainings = new ArrayList<>();

        String[] columns = { TRAINING_ID};
        String where = USER_ID + "=\"" + userId + "\"";

        Cursor cursor = db.query(TABLE_USER_TRAININGS, columns, where, null, null, null, null);

        if(cursor.moveToFirst()){
            do {
                trainings.add(trainingsDAO.getTraining(cursor.getString(0)));
            } while(cursor.moveToNext());

            cursor.close();
        }
        return trainings;
    }

    @Override
    public void close() {
        trainingsDAO.close();
        super.close();
    }
}
