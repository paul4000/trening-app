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

        authorization = new Authorization();
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

//        if (!authorization.checkIfLogged(sharedPreferences.getString("JWT_TOKEN", null))) {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(intent);
//            return;
//        }


        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button toWebTrainings = (Button) findViewById(R.id.browseTrain);
        toWebTrainings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebTrainingsActivity.class);
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