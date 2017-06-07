package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.adapters.PlannedTrainingsArrayAdapter;
import com.example.assistant.workout_assistant.authorization.Authorization;
import com.example.assistant.workout_assistant.database.tables.PlannedTrainingsDAO;
import com.example.assistant.workout_assistant.bo.PlannedTraining;

import java.util.List;

public class PlannedTrainingsActivity extends AppCompatActivity {


    List<PlannedTraining> trainings;
    PlannedTrainingsDAO trainingsDAO = new PlannedTrainingsDAO(this);
    ListView trainingsList;


    SharedPreferences sharedPreferences;
    Authorization authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_trainings);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);

        if (!authorization.isLogged()) {
            authorization.askLogin(this);
        }


        trainingsList = (ListView) findViewById(R.id.plannedTrainingList);
        trainings = trainingsDAO.getPlannedTrainingsForUser();

        trainingsList.setAdapter(new PlannedTrainingsArrayAdapter(this, trainings, trainingsDAO));


    }

    @Override
    protected void onDestroy() {
        trainingsDAO.close();
        super.onDestroy();
    }
}
