package com.example.assistant.workout_assistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assistant.workout_assistant.database.DBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
