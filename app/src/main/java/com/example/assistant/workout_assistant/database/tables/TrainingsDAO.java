package com.example.assistant.workout_assistant.database.tables;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assistant.workout_assistant.exercises.Training;

import java.util.ArrayList;
import java.util.List;

public class TrainingsDAO extends DAO {

    private ExerciseBeanDAO exerciseBeanDAO;

    //NAME
    public static final String TABLE_TRAINING = "trainings";
    //COLUMNS
    public static final String AUTHOR = "author";
    public static final String UPDATE = "updated";

    public static String CREATE_QUERY = CREATE + TABLE_TRAINING + "("
                + KEY_ID + " TEXT PRIMARY KEY, "
                + NAME + " TEXT, "
                + AUTHOR + " TEXT, "
                + UPDATE + " TEXT)";

    public static String DELETE_QUERY = DELETE + TABLE_TRAINING;

    public TrainingsDAO(Context context) {
        super(context);
        exerciseBeanDAO = new ExerciseBeanDAO(context);
    }

    public boolean trainingExist(String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =
                db.rawQuery(SELECT_EXISTING + TABLE_TRAINING + " where " + KEY_ID + "=?",
                        new String[]{ id });
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }

    public boolean insertTraining(Training training){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = fillValues(training);

        if(db.insert(TABLE_TRAINING, null, values) == -1) return false;

        List<Training.ExercisesBean> exercisesBeans = training.getExercises();

        for(Training.ExercisesBean bean: exercisesBeans){
            if(!exerciseBeanDAO.insertExerciseBean(bean, training.get_id())) return false;
        }

        return true;
    }

    public boolean deleteTraining(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delete = db.delete(TrainingsDAO.TABLE_TRAINING, TrainingsDAO.KEY_ID + " = ?", new String[]{id});
        return delete == 1;
    }
    public Training getTraining(String trainingId){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String author="";
        String updated="";
        String name="";

        String[] columns = { TrainingsDAO.KEY_ID, TrainingsDAO.AUTHOR, TrainingsDAO.NAME,
                TrainingsDAO.UPDATE };

        String where = TrainingsDAO.KEY_ID + "=\"" + trainingId + "\"";
        Cursor cursor =
                db.query(TrainingsDAO.TABLE_TRAINING, columns, where, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            author = cursor.getString(1);
            name = cursor.getString(2);
            updated = cursor.getString(3);
            cursor.close();
        }
        List<Training.ExercisesBean> beans = exerciseBeanDAO.getExerciseBeans(trainingId);

        return new Training(trainingId, author, name, 0, updated, beans);

    }

    public List<Training> getMyTrainings(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<Training> trainings = new ArrayList<>();

        String[] columns = { TrainingsDAO.KEY_ID };
        Cursor cursor = db.query(TrainingsDAO.TABLE_TRAINING, columns, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                trainings.add(getTraining(cursor.getString(0)));
            }while(cursor.moveToNext());

            cursor.close();
        }

        return trainings;
    }

    public ContentValues fillValues(Training training) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, training.get_id());
        contentValues.put(NAME, training.getName());
        contentValues.put(AUTHOR, training.getAuthorName());
        contentValues.put(UPDATE, training.getUpdated());

        return contentValues;

    }

    @Override
    public void close() {
        exerciseBeanDAO.close();
        super.close();
    }
}
