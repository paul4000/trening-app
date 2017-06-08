package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.adapters.TrainigExercisesArrayAdapter;
import com.example.assistant.workout_assistant.bo.SeriesBean;
import com.example.assistant.workout_assistant.bo.Training;
import com.example.assistant.workout_assistant.database.tables.TrainingsDAO;

import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    ListView trainingExercises;


    SharedPreferences sharedPreferences;

    TrainingsDAO trainingsDAO;

    Button abandon;
    Button finish;

    List<Training.ExercisesBean> exercises;

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


        for (Training.SeriesIter iter = training.getSeriesIterator(); iter.hasNext(); ) {
            SeriesBean seriesBean = iter.next();

            Log.e("WA", "" + seriesBean.getLoad());
            Log.e("WA", "" + seriesBean.getQuantity());
            Log.e("WA", "" + seriesBean.getTime());

        }


        abandon = (Button) findViewById(R.id.abandon);
        finish = (Button) findViewById(R.id.finish);

        abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("ACTUAL_TRAINING_ID");
                editor.commit();
                clearTrainingHistory();

                onBackPressed();
            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("ACTUAL_TRAINING_ID");
                editor.commit();

                clearTrainingHistory();
//                Toast.makeText(getContext, "Zakonczyles trening", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

        if (training == null) {
            Log.e("WA", "training nieznaleziony");
            return;
        } else {
            Log.e("WA", "training znaleziony");
        }

        exercises = training.getExercises();

        trainingExercises = (ListView)

                findViewById(R.id.training_exercises);
        trainingExercises.setAdapter(new

                TrainigExercisesArrayAdapter(WorkoutActivity.this, exercises));


        Log.e("WA", "H");
        Log.e("WA", "e");
    }


    public void clearTrainingHistory() {
        for (int i = 0; i < exercises.size(); ++i) {
            List<SeriesBean> seriesBeen = exercises.get(i).getSeries();
            for (int j = 0; j < seriesBeen.size(); ++j) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("ACTUAL_TRAINING_CHECK_" + i + "_" + j);
                editor.commit();
            }
        }
    }
}
