package com.example.assistant.workout_assistant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assistant.workout_assistant.database.tables.ExeMusTableManager;
import com.example.assistant.workout_assistant.database.tables.ExeReqTableManager;
import com.example.assistant.workout_assistant.database.tables.ExerciseBeanTableManager;
import com.example.assistant.workout_assistant.database.tables.ExerciseTableManager;
import com.example.assistant.workout_assistant.database.tables.MusclesTableManager;
import com.example.assistant.workout_assistant.database.tables.RequirementsTableManager;
import com.example.assistant.workout_assistant.database.tables.SeriesTableManager;
import com.example.assistant.workout_assistant.database.tables.TrainingsTableManager;
import com.example.assistant.workout_assistant.exercises.Exercise;
import com.example.assistant.workout_assistant.exercises.SeriesBean;
import com.example.assistant.workout_assistant.exercises.Training;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //DATABASE VERSION
    private static final int DATABASE_VERSION = 6;
    //DATABASE NAME
    private static final String DATABASE_NAME = "trainingsManager";
    //SAMPLE QUERY
    private static final String SELECT_EXISTING = "select 1 from ";

    //TABLES
    private TrainingsTableManager trainings = new TrainingsTableManager();
    private ExerciseBeanTableManager exerciseBeans = new ExerciseBeanTableManager();
    private ExerciseTableManager exercises = new ExerciseTableManager();
    private RequirementsTableManager requirements = new RequirementsTableManager();
    private MusclesTableManager muscles = new MusclesTableManager();
    private SeriesTableManager series = new SeriesTableManager();
    private ExeMusTableManager exe_mus = new ExeMusTableManager();
    private ExeReqTableManager exe_req = new ExeReqTableManager();


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        trainings.create(db);
        exercises.create(db);
        exerciseBeans.create(db);
        series.create(db);
        requirements.create(db);
        muscles.create(db);
        exe_mus.create(db);
        exe_req.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        exe_mus.delete(db);
        exe_req.delete(db);
        series.delete(db);
        requirements.delete(db);
        muscles.delete(db);
        exercises.delete(db);
        exerciseBeans.delete(db);
        trainings.delete(db);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    public List<Training> getMyTrainings(){

        SQLiteDatabase db = getReadableDatabase();

        List<Training> trainings = new ArrayList<>();

        String[] columns = { TrainingsTableManager.KEY_ID };
        Cursor cursor = db.query(TrainingsTableManager.TABLE_TRAINING, columns, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                trainings.add(getTraining(cursor.getString(0)));
            }while(cursor.moveToNext());

            cursor.close();
        }

        return trainings;
    }

    public Training getTraining(String trainingId){

        SQLiteDatabase db = getReadableDatabase();

        String author="";
        String updated="";
        String name="";

        String[] columns = { TrainingsTableManager.KEY_ID, TrainingsTableManager.AUTHOR, TrainingsTableManager.NAME,
        TrainingsTableManager.UPDATE };

        String where = TrainingsTableManager.KEY_ID + "=\"" + trainingId + "\"";
        Cursor cursor =
                db.query(TrainingsTableManager.TABLE_TRAINING, columns, where, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            author = cursor.getString(1);
            name = cursor.getString(2);
            updated = cursor.getString(3);
            cursor.close();
        }
        List<Training.ExercisesBean> beans = getExerciseBeans(trainingId);

        return new Training(trainingId, author, name, 0, updated, beans);

    }

    private List<Training.ExercisesBean> getExerciseBeans(String trainingId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Training.ExercisesBean> exercisesBeenList = new ArrayList<>();

        String[] columns = {ExerciseBeanTableManager.KEY_ID, ExerciseBeanTableManager.EXERCISE_ID,
                ExerciseBeanTableManager.TIME, ExerciseBeanTableManager.QUANTITY, ExerciseBeanTableManager.LOAD };
        String where = ExerciseBeanTableManager.TRAINING_ID + "=\"" + trainingId + "\"";
        Cursor cursor =
                db.query(ExerciseBeanTableManager.TABLE_EXERCISE_BEAN, columns, where, null, null, null, null);

        if(cursor.moveToFirst()){
            do {
                int beanId = cursor.getInt(0);
                Exercise exercise = getExercise(cursor.getString(1));
                boolean time = cursor.getInt(2) == 1;
                boolean quantity = cursor.getInt(3) == 1;
                boolean load = cursor.getInt(4) == 1;
                List<SeriesBean> series = getSeries(beanId);

                exercisesBeenList.add(new Training.ExercisesBean(exercise, time, quantity, load, "", series));

            }while(cursor.moveToNext());

            cursor.close();
        }

        return exercisesBeenList;
    }

    private List<SeriesBean> getSeries(int beanId) {
        SQLiteDatabase db = getReadableDatabase();
        List<SeriesBean> seriesBeen = new ArrayList<>();

        String[] columns = {SeriesTableManager.TIME, SeriesTableManager.QUANTITY, SeriesTableManager.LOAD};
        String where = SeriesTableManager.EXE_BEAN_ID + "=" + beanId;

        Cursor cursor = db.query(SeriesTableManager.TABLE_SERIES, columns, where, null, null, null, null);

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

    public Exercise getExercise(String exerciseId){
        SQLiteDatabase db = getReadableDatabase();

        String description="";
        String name="";
        String place="";
        List<String> muscles = new ArrayList<>();
        List<String> reqs = new ArrayList<>();

        String[] columns = { ExerciseTableManager.DESCRIPTION, ExerciseTableManager.NAME, ExerciseTableManager.PLACE };
        String where = ExerciseTableManager.KEY_ID + "=\""+ exerciseId + "\"";
        Cursor cursor =
                db.query(ExerciseTableManager.TABLE_EXERCISE, columns, where, null, null, null, null);

        if(cursor.moveToFirst()){
            description = cursor.getString(0);
            name = cursor.getString(1);
            place = cursor.getString(2);
            cursor.close();
        }

        String queryForMuscles = exe_mus.getQueryForExercisesAndMuscles();

        Cursor cursorMuscles = db.rawQuery(queryForMuscles + " where exercise_id =?", new String[] { exerciseId });

        if(cursorMuscles.moveToFirst()){
            do{
                muscles.add(cursorMuscles.getString(1));
            }while (cursorMuscles.moveToNext());
            cursorMuscles.close();
        }

        String queryForRequirements = exe_req.getQueryForExercisesAndRequirements();

        Cursor cursorReq = db.rawQuery(queryForRequirements + " where exercise_id =?", new String[] { exerciseId });

        if(cursorReq.moveToFirst()){
            do{
                reqs.add(cursorReq.getString(1));
            }while (cursorReq.moveToNext());
            cursorReq.close();
        }
        return new Exercise(exerciseId, description, name, place, 0, reqs, muscles);

    }

    public boolean insertTraining(Training training){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = trainings.fillValues(training);

        if(db.insert(trainings.getTableName(), null, values) == -1) return false;

        List<Training.ExercisesBean> exercisesBeans = training.getExercises();

        for(Training.ExercisesBean bean: exercisesBeans){
            if(!insertExerciseBean(bean, training.get_id())) return false;
        }

        return true;
    }

    private boolean insertExerciseBean(Training.ExercisesBean bean, String trainingId) {
        SQLiteDatabase db = getWritableDatabase();

        Exercise exercise = bean.getExercise();

        if(!exerciseExist(exercise.get_id()))
            if (!insertExercise(exercise)) return false;

        ContentValues contentValues = exerciseBeans.fillValues(bean, trainingId);
        long beanId = db.insert(exerciseBeans.getTableName(), null, contentValues);
        if(beanId == -1) return false;

        List<SeriesBean> series = bean.getSeries();

        for(SeriesBean s: series){
            if(!insertSeries(s, beanId)) return false;
        }
        return true;

    }

    private boolean insertSeries(SeriesBean s, long beanId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = series.fillValues(s, beanId);

        return db.insert(series.getTableName(), null, contentValues) != -1;
    }

    private boolean insertExercise(Exercise exercise) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = exercises.fillValues(exercise);

        long exId = db.insert(exercises.getTableName(), null, contentValues);
        if(exId == -1) return false;

        List<String> musc = exercise.getMuscles();
        for(String muscle: musc){
            long muscleId = muscles.getId(getReadableDatabase(), muscle);
            if(muscleId == -1) muscleId = insertMuscle(muscle);
            if(muscleId == -1) return false;

            if(!insertExeMusl(exercise.get_id(), muscleId)) return false;
        }

        List<String> reqs = exercise.getRequirements();
        for(String r: reqs){
            long reqId = requirements.getId(getReadableDatabase(), r);
            if(reqId == -1) reqId = insertRequirement(r);
            if(reqId == -1) return false;

            if(!insertExeReq(exercise.get_id(), reqId)) return false;
        }

        return true; 

    }

    private boolean insertExeReq(String id, long reqId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = exe_req.fillContent(id, reqId);

        return db.insert(exe_req.getTableName(), null, contentValues) != -1;
    }

    private long insertRequirement(String req) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = requirements.fillContent(req);

        return db.insert(requirements.getTableName(), null, contentValues);
    }

    private boolean insertExeMusl(String id, long muscleId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = exe_mus.fillContent(id, muscleId);

        return db.insert(exe_mus.getTableName(), null, contentValues) != -1;
    }

    private long insertMuscle(String muscle) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = muscles.fillContent(muscle);

        return db.insert(muscles.getTableName(), null, contentValues);
    }

    public boolean exerciseExist(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =
                db.rawQuery(SELECT_EXISTING + exercises.getTableName() + " where " + exercises.getKeyColumnName() + "=?",
                        new String[]{ id });
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }

    public boolean trainingExist(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =
                db.rawQuery(SELECT_EXISTING + trainings.getTableName() + " where " + trainings.getKeyColumnName() + "=?",
                        new String[]{ id });
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }


    public void close(){
        SQLiteDatabase db = getReadableDatabase();
        db.close();
    }
}
