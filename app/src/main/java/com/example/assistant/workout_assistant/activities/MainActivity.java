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
        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);

        boolean isLogged = sharedPreferences.getBoolean("LOGGED", false);
        String token = sharedPreferences.getString("JWT_TOKEN", null);

        if (!isLogged) {
            authorization.askLogin(this);
        }

//        Button login = (Button) findViewById(R.id.login);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//        login.setEnabled(!isLogged);


        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorization.signOut(MainActivity.this, sharedPreferences);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        logout.setEnabled(isLogged);

//        Button toWebTrainings = (Button) findViewById(R.id.browseTrain);
//        toWebTrainings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, WebTrainingsActivity.class);
//                startActivity(intent);
//            }
//        });

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
