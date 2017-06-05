package com.example.assistant.workout_assistant.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ExeReqDAO extends DAO {

    public static final String TABLE_EXE_REQ = "exe_req";

    //COLUMNS
    public static final String REQ_ID = "requirement_id";

    public static String CREATE_QUERY  = CREATE + TABLE_EXE_REQ + " ("
                + KEY_ID + " INTEGER PRIMARY_KEY, "
                + EXERCISE_ID + " TEXT, "
                + REQ_ID + " INTEGER, "
                + "FOREIGN KEY (" + EXERCISE_ID + ") REFERENCES exercises(" + KEY_ID +") " + DELETE_CASCADE + ", "
                + "FOREIGN KEY (" + REQ_ID +") REFERENCES requirements(" + KEY_ID + ") " + DELETE_CASCADE+ ")";

    public static String DELETE_QUERY  = DELETE + TABLE_EXE_REQ;

    public static String JOIN_EXERCISES_REQUIREMENTS_QUERY = "select " + ExerciseDAO.TABLE_EXERCISE+ "."+ ExerciseDAO.KEY_ID + " as exercise_id, "
            + RequirementsDAO.TABLE_REQUIREMENTS + "." + RequirementsDAO.NAME + " as requirement"+
            " from " + ExeReqDAO.TABLE_EXE_REQ
            + " inner join " + ExerciseDAO.TABLE_EXERCISE + " on " +
            ExerciseDAO.TABLE_EXERCISE+"."+ ExerciseDAO.KEY_ID + "=" + ExeReqDAO.TABLE_EXE_REQ + "."+ ExeReqDAO.EXERCISE_ID
            + " inner join " + RequirementsDAO.TABLE_REQUIREMENTS + " on " +
            RequirementsDAO.TABLE_REQUIREMENTS+"."+ RequirementsDAO.KEY_ID + "=" + ExeReqDAO.TABLE_EXE_REQ + "."+ ExeReqDAO.REQ_ID;

    protected ExeReqDAO(Context context) {
        super(context);
    }

    public boolean insertExeReq(String id, long reqId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = fillContent(id, reqId);

        return db.insert(TABLE_EXE_REQ, null, contentValues) != -1;
    }

    public List<String> getRequirementsForExercise(String exerciseId){

        List<String> reqs = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursorReq = db.rawQuery(JOIN_EXERCISES_REQUIREMENTS_QUERY + " where exercise_id =?", new String[] { exerciseId });

        if(cursorReq.moveToFirst()){
            do{
                reqs.add(cursorReq.getString(1));
            }while (cursorReq.moveToNext());
            cursorReq.close();
        }
        return reqs;
    }

    public ContentValues fillContent(String id, long reqId) {
        ContentValues c = new ContentValues();
        c.put(EXERCISE_ID, id);
        c.put(REQ_ID, reqId);
        return c;
    }
}
