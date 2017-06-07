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

public class WebResourcesActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Authorization authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_resources);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);

        if (!authorization.isLogged()) {
            authorization.askLogin(this);
        }

        Button toWebTrainings = (Button) findViewById(R.id.browseTrain);
        toWebTrainings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebResourcesActivity.this, WebTrainingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
