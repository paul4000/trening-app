package com.example.assistant.workout_assistant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assistant.workout_assistant.database.tables.ExeMusDAO;
import com.example.assistant.workout_assistant.database.tables.ExeReqDAO;
import com.example.assistant.workout_assistant.database.tables.ExerciseBeanDAO;
import com.example.assistant.workout_assistant.database.tables.ExerciseDAO;
import com.example.assistant.workout_assistant.database.tables.MusclesDAO;
import com.example.assistant.workout_assistant.database.tables.PlannedTrainingsDAO;
import com.example.assistant.workout_assistant.database.tables.RequirementsDAO;
import com.example.assistant.workout_assistant.database.tables.SeriesDAO;
import com.example.assistant.workout_assistant.database.tables.TrainingsDAO;

public class DBHelper extends SQLiteOpenHelper {

    //DATABASE VERSION
    private static final int DATABASE_VERSION = 9;
    //DATABASE NAME
    private static final String DATABASE_NAME = "trainingsManager";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TrainingsDAO.CREATE_QUERY);
        db.execSQL(ExerciseDAO.CREATE_QUERY);
        db.execSQL(ExerciseBeanDAO.CREATE_QUERY);
        db.execSQL(SeriesDAO.CREATE_QUERY);
        db.execSQL(RequirementsDAO.CREATE_QUERY);
        db.execSQL(MusclesDAO.CREATE_QUERY);
        db.execSQL(ExeMusDAO.CREATE_QUERY);
        db.execSQL(ExeReqDAO.CREATE_QUERY);
        db.execSQL(PlannedTrainingsDAO.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PlannedTrainingsDAO.DELETE_QUERY);
        db.execSQL(ExeMusDAO.DELETE_QUERY);
        db.execSQL(ExeReqDAO.DELETE_QUERY);
        db.execSQL(SeriesDAO.DELETE_QUERY);
        db.execSQL(RequirementsDAO.DELETE_QUERY);
        db.execSQL(MusclesDAO.DELETE_QUERY);
        db.execSQL(ExerciseDAO.DELETE_QUERY);
        db.execSQL(ExerciseBeanDAO.DELETE_QUERY);
        db.execSQL(TrainingsDAO.DELETE_QUERY);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }


    public void close(){
        SQLiteDatabase db = getReadableDatabase();
        db.close();
    }

}
