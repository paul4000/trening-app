package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.authorization.Authorization;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Authorization authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);

        if (!authorization.isLogged()) {
            authorization.askLogin(this);
            return;
        }


        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    }


}
