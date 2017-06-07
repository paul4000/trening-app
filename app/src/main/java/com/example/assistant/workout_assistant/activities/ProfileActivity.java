package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.authorization.Authorization;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Authorization authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);

        if (!authorization.isLogged()) {
            authorization.askLogin(this);
        }

    }
}
