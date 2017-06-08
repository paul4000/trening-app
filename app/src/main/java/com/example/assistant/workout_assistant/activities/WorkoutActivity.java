package com.example.assistant.workout_assistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.adapters.TrainigExercisesArrayAdapter;
import com.example.assistant.workout_assistant.bo.Training;

import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    ListView trainingExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();

        Training training = (Training) intent.getSerializableExtra("TRAINING");


        if (training == null) {
            Log.e("WA", "training nieznaleziony");
            return;
        } else {
            Log.e("WA", "training znaleziony");
        }


        List<Training.ExercisesBean> exercises = training.getExercises();

        trainingExercises = (ListView) findViewById(R.id.training_exercises);
        trainingExercises.setAdapter(new TrainigExercisesArrayAdapter(WorkoutActivity.this, exercises));

        Log.e("WA", "H");
        Log.e("WA", "e");
    }
}
