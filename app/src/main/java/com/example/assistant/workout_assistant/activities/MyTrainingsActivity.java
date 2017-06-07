package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.adapters.TrainingsArrayAdapter;
import com.example.assistant.workout_assistant.authorization.Authorization;
import com.example.assistant.workout_assistant.database.tables.TrainingsDAO;
import com.example.assistant.workout_assistant.bo.Training;
import com.example.assistant.workout_assistant.database.tables.UserTrainingsDAO;

import java.util.List;

public class MyTrainingsActivity extends AppCompatActivity {

    List<Training> trainings;
    UserTrainingsDAO trainingsDAO = new UserTrainingsDAO(this);
    ListView trainingsList;


    SharedPreferences sharedPreferences;
    Authorization authorization;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trainings);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);

        userId = authorization.getUser().getId();

        if (!authorization.isLogged()) {
            authorization.askLogin(this);
        }


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

    public void loadTraining() {
        trainings = trainingsDAO.getUserTrainings(userId);
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
