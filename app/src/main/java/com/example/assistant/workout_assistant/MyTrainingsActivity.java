package com.example.assistant.workout_assistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.assistant.workout_assistant.adapters.TrainingsArrayAdapter;
import com.example.assistant.workout_assistant.database.DBHelper;
import com.example.assistant.workout_assistant.database.tables.TrainingsDAO;
import com.example.assistant.workout_assistant.exercises.Training;

import java.util.List;

public class MyTrainingsActivity extends AppCompatActivity {

    List<Training> trainings;
    TrainingsDAO trainingsDAO = new TrainingsDAO(this);
    ListView trainingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trainings);

        trainingsList = (ListView) findViewById(R.id.myTrainingsList);

        loadTraining();

        trainingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyTrainingsActivity.this, TrainingDetailsActivity.class);
                Training trainingAtPosition = (Training) parent.getItemAtPosition(position);
                intent.putExtra("TRAINING", trainingAtPosition);
                intent.putExtra("MODE", "EDIT");

                startActivity(intent);
            }
        });


    }

    public void loadTraining(){
            trainings = trainingsDAO.getMyTrainings();
            trainingsList.setAdapter(new TrainingsArrayAdapter(this, trainings));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTraining();
    }

    @Override
    protected void onDestroy() {
        trainingsDAO.close();
        super.onDestroy();
    }
}
