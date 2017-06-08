package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.authorization.Authorization;
import com.example.assistant.workout_assistant.database.tables.PlannedTrainingsDAO;
import com.example.assistant.workout_assistant.notifications.NotificationsConfigurator;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Authorization authorization;
    NotificationsConfigurator notificationsConfigurator;
    String userId;

    PlannedTrainingsDAO plannedTrainingsDAO;

    Button continueActualTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);
        plannedTrainingsDAO = new PlannedTrainingsDAO(this);

        if (!authorization.isLogged()) {
            authorization.askLogin(this);
            return;
        }

        notificationsConfigurator = new NotificationsConfigurator(this);
        userId = authorization.getUser().getId();


        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postponeAllNotifications(userId);
                authorization.signOut(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button toProfile = (Button) findViewById(R.id.profile);
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        Button toWebResources = (Button) findViewById(R.id.webResources);
        toWebResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebResourcesActivity.class);
                startActivity(intent);
            }
        });

        Button toMyTrainings = (Button) findViewById(R.id.browseMyTrain);
        toMyTrainings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyTrainingsActivity.class);
                startActivity(intent);
            }
        });

        Button toPlannedTrainings = (Button) findViewById(R.id.browsePlannedTrain);
        toPlannedTrainings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlannedTrainingsActivity.class);
                startActivity(intent);
            }
        });
        continueActualTraining = (Button) findViewById(R.id.continue_actual_training);
        continueActualTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        String actualTrainingId = sharedPreferences.getString("ACTUAL_TRAINING_ID", null);
        if (actualTrainingId != null) {
            continueActualTraining.setVisibility(View.VISIBLE);
        } else {
            continueActualTraining.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        plannedTrainingsDAO.close();
        super.onDestroy();
    }

    private void postponeAllNotifications(String userId) {

        List<Integer> notificationsList = plannedTrainingsDAO.getAllNotificationsForUser(userId);

        for (int notification : notificationsList) {
            notificationsConfigurator.cancelNotification(this, notification);
        }

    }


}
