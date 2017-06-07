package com.example.assistant.workout_assistant.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.adapters.PlannedTrainingsArrayAdapter;
import com.example.assistant.workout_assistant.database.tables.PlannedTrainingsDAO;
import com.example.assistant.workout_assistant.database.tables.TrainingsDAO;
import com.example.assistant.workout_assistant.exercises.PlannedTraining;
import com.example.assistant.workout_assistant.exercises.Training;

import java.util.List;

public class PlannedTrainingsActivity extends AppCompatActivity {


    List<PlannedTraining> trainings;
    PlannedTrainingsDAO trainingsDAO = new PlannedTrainingsDAO(this);
    ListView trainingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_trainings);

        trainingsList = (ListView) findViewById(R.id.plannedTrainingList);
        trainings = trainingsDAO.getPlannedTrainings();

        trainingsList.setAdapter(new PlannedTrainingsArrayAdapter(this, trainings, trainingsDAO));


    }

    @Override
    protected void onDestroy() {
        trainingsDAO.close();
        super.onDestroy();
    }
}
