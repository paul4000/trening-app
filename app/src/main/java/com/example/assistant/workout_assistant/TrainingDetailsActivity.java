package com.example.assistant.workout_assistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assistant.workout_assistant.adapters.ExerciseBeanArrayAdapter;
import com.example.assistant.workout_assistant.database.DBHelper;
import com.example.assistant.workout_assistant.exercises.Training;

public class TrainingDetailsActivity extends AppCompatActivity {

    private Training training;
    private DBHelper dbHelper = new DBHelper(this);
    private Button saveButton;

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_details);

        Bundle bundle = getIntent().getExtras();

        training = (Training) bundle.getSerializable("TRAINING");

        TextView nameView = (TextView) findViewById(R.id.trainingName);
        nameView.setText(training.getName());

        TextView authorName = (TextView) findViewById(R.id.author);
        authorName.setText(training.getAuthor());

        ListView exercisesList = (ListView) findViewById(R.id.exerciseBeansList);
        exercisesList.setAdapter(new ExerciseBeanArrayAdapter(TrainingDetailsActivity.this, training.getExercises()));

        
        saveButton = (Button) findViewById(R.id.save);
        if(dbHelper.trainingExist(training.get_id())) disableButtonIfCannotDownload();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info;
                boolean downloaded = dbHelper.insertTraining(training);
                if(downloaded) info = getString(R.string.success_download);
                else info = getString(R.string.error_download);
                Toast.makeText(TrainingDetailsActivity.this, info, Toast.LENGTH_SHORT).show();

                if(downloaded) disableButtonIfCannotDownload();
            }
        });

    }

    private void disableButtonIfCannotDownload() {
            saveButton.setEnabled(false);
            TextView alreadySaved = (TextView) findViewById(R.id.already_saved);
            alreadySaved.setVisibility(View.VISIBLE);

    }
}
