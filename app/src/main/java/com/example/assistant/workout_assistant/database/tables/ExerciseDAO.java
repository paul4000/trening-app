package com.example.assistant.workout_assistant.database.tables;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.example.assistant.workout_assistant.exercises.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO extends DAO {

    private MusclesDAO musclesDAO;
    private RequirementsDAO requirementsDAO;
    private ExeMusDAO exeMusDAO;
    private ExeReqDAO exeReqDAO;

    public static final String TABLE_EXERCISE = "exercises";
    //COLUMNS
    public static final String DESCRIPTION = "description";
    public static final String PLACE = "place";

    public static String CREATE_QUERY =  CREATE + TABLE_EXERCISE + "("
                + KEY_ID + " TEXT PRIMARY KEY, "
                + NAME + " TEXT, "
                + PLACE + " TEXT, "
                + DESCRIPTION + " TEXT)";

    public static String DELETE_QUERY = DELETE + TABLE_EXERCISE;

    protected ExerciseDAO(Context context) {
        super(context);
        musclesDAO = new MusclesDAO(context);
        requirementsDAO = new RequirementsDAO(context);
        exeReqDAO = new ExeReqDAO(context);
        exeMusDAO = new ExeMusDAO(context);
    }

    public boolean exerciseExist(String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =
                db.rawQuery(SELECT_EXISTING + TABLE_EXERCISE + " where " + KEY_ID + "=?",
                        new String[]{ id });
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }
    public boolean insertExercise(Exercise exercise) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = fillValues(exercise);

        long exId = db.insert(TABLE_EXERCISE, null, contentValues);
        if(exId == -1) return false;

        List<String> musc = exercise.getMuscles();
        for(String muscle: musc){
            long muscleId = musclesDAO.getId(muscle);
            if(muscleId == -1) muscleId = musclesDAO.insertMuscle(muscle);
            if(muscleId == -1) return false;

            if(!exeMusDAO.insertExeMusl(exercise.get_id(), muscleId)) return false;
        }

        List<String> reqs = exercise.getRequirements();
        for(String r: reqs){
            long reqId = requirementsDAO.getId(r);
            if(reqId == -1) reqId = requirementsDAO.insertRequirement(r);
            if(reqId == -1) return false;

            if(!exeReqDAO.insertExeReq(exercise.get_id(), reqId)) return false;
        }

        return true;

    }

    public Exercise getExercise(String exerciseId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String description="";
        String name="";
        String place="";
        List<String> muscles;
        List<String> reqs;

        String[] columns = { ExerciseDAO.DESCRIPTION, ExerciseDAO.NAME, ExerciseDAO.PLACE };
        String where = ExerciseDAO.KEY_ID + "=\""+ exerciseId + "\"";
        Cursor cursor =
                db.query(ExerciseDAO.TABLE_EXERCISE, columns, where, null, null, null, null);

        if(cursor.moveToFirst()){
            description = cursor.getString(0);
            name = cursor.getString(1);
            place = cursor.getString(2);
            cursor.close();
        }

        muscles = exeMusDAO.getMusclesForExercise(exerciseId);
        reqs = exeReqDAO.getRequirementsForExercise(exerciseId);

        return new Exercise(exerciseId, description, name, place, 0, reqs, muscles);
    }

    public ContentValues fillValues(Exercise exercise) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, exercise.get_id());
        contentValues.put(NAME, exercise.getName());
        contentValues.put(PLACE, exercise.getPlace());
        contentValues.put(DESCRIPTION, exercise.getDescription());
        return contentValues;

    }

    @Override
    public void close() {
        musclesDAO.close();
        requirementsDAO.close();
        exeMusDAO.close();
        exeReqDAO.close();
        super.close();
    }
}
