package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.adapters.TrainigExercisesArrayAdapter;
import com.example.assistant.workout_assistant.bo.Training;
import com.example.assistant.workout_assistant.database.tables.TrainingsDAO;

import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    ListView trainingExercises;


    SharedPreferences sharedPreferences;
//    Authorization authorization;

    TrainingsDAO trainingsDAO;

    Button abandon;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        trainingsDAO = new TrainingsDAO(this);
        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);

//        ActualTraining actualTraining = null;
        Training training = null;

        String actualTrainingId = sharedPreferences.getString("ACTUAL_TRAINING_ID", null);

        if (actualTrainingId != null && trainingsDAO.trainingExist(actualTrainingId)) {
            training = trainingsDAO.getTraining(actualTrainingId);
        } else {
            Intent intent = getIntent();

            training = (Training) intent.getSerializableExtra("TRAINING");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ACTUAL_TRAINING_ID", training.get_id());
            editor.commit();
        }

        abandon = (Button) findViewById(R.id.abandon);
        finish = (Button) findViewById(R.id.finish);

        abandon.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("ACTUAL_TRAINING_ID");
            editor.commit();

            onBackPressed();
        });


        finish.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("ACTUAL_TRAINING_ID");
            editor.commit();

            Toast.makeText(this, "Zakonczyles trening", Toast.LENGTH_LONG).show();
            onBackPressed();
        });

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
