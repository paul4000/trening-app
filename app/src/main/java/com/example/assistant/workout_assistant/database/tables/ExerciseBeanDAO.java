package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assistant.workout_assistant.exercises.Exercise;
import com.example.assistant.workout_assistant.exercises.SeriesBean;
import com.example.assistant.workout_assistant.exercises.Training;

import java.util.ArrayList;
import java.util.List;

public class ExerciseBeanDAO extends DAO {

    private ExerciseDAO exerciseDAO;
    private SeriesDAO seriesDAO;

     public static final String TABLE_EXERCISE_BEAN = "exercise_beans";

    //COLUMNS
     public static final String TRAINING_ID = "training_id";

    public static String CREATE_QUERY  = CREATE + TABLE_EXERCISE_BEAN + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + EXERCISE_ID + " TEXT, "
                + TIME + " INTEGER, "
                + LOAD + " INTEGER, "
                + QUANTITY + " INTEGER, "
                + TRAINING_ID + " TEXT, "
                + "FOREIGN KEY (" + EXERCISE_ID + ") REFERENCES exercises(" + KEY_ID +") " + DELETE_CASCADE + ", "
                + "FOREIGN KEY (" + TRAINING_ID +") REFERENCES trainings(" + KEY_ID + ") "+ DELETE_CASCADE +")";

    public static String DELETE_QUERY = DELETE + TABLE_EXERCISE_BEAN;

    protected ExerciseBeanDAO(Context context) {
        super(context);
        exerciseDAO = new ExerciseDAO(context);
        seriesDAO = new SeriesDAO(context);
    }


    public boolean insertExerciseBean(Training.ExercisesBean bean, String trainingId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Exercise exercise = bean.getExercise();

        if(!exerciseDAO.exerciseExist(exercise.get_id()))
            if (!exerciseDAO.insertExercise(exercise)) return false;

        ContentValues contentValues = fillValues(bean, trainingId);
        long beanId = db.insert(TABLE_EXERCISE_BEAN, null, contentValues);
        if(beanId == -1) return false;

        List<SeriesBean> series = bean.getSeries();

        for(SeriesBean s: series){
            if(!seriesDAO.insertSeries(s, beanId)) return false;
        }
        return true;
    }

    public List<Training.ExercisesBean> getExerciseBeans(String trainingId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Training.ExercisesBean> exercisesBeenList = new ArrayList<>();

        String[] columns = {ExerciseBeanDAO.KEY_ID, ExerciseBeanDAO.EXERCISE_ID,
                ExerciseBeanDAO.TIME, ExerciseBeanDAO.QUANTITY, ExerciseBeanDAO.LOAD };
        String where = ExerciseBeanDAO.TRAINING_ID + "=\"" + trainingId + "\"";
        Cursor cursor =
                db.query(ExerciseBeanDAO.TABLE_EXERCISE_BEAN, columns, where, null, null, null, null);

        if(cursor.moveToFirst()){
            do {
                int beanId = cursor.getInt(0);
                Exercise exercise = exerciseDAO.getExercise(cursor.getString(1));
                boolean time = cursor.getInt(2) == 1;
                boolean quantity = cursor.getInt(3) == 1;
                boolean load = cursor.getInt(4) == 1;
                List<SeriesBean> series = seriesDAO.getSeries(beanId);

                exercisesBeenList.add(new Training.ExercisesBean(exercise, time, quantity, load, "", series));

            }while(cursor.moveToNext());

            cursor.close();
        }

        return exercisesBeenList;
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

    @Override
    public void close() {
        exerciseDAO.close();
        seriesDAO.close();
        super.close();
    }
}
