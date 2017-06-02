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

        DBHelper dbHelper = new DBHelper(this);

    }


}
