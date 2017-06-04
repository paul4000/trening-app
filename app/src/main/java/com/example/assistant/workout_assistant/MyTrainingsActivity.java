package com.example.assistant.workout_assistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.assistant.workout_assistant.adapters.TrainingsArrayAdapter;
import com.example.assistant.workout_assistant.database.DBHelper;
import com.example.assistant.workout_assistant.exercises.Training;

import java.util.List;

public class MyTrainingsActivity extends AppCompatActivity {

    List<Training> trainings;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trainings);

        loadTraining();

        ListView trainingsList = (ListView) findViewById(R.id.myTrainingsList);
        trainingsList.setAdapter(new TrainingsArrayAdapter(this, trainings));

    }

    public void loadTraining(){
            trainings = dbHelper.getMyTrainings();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
