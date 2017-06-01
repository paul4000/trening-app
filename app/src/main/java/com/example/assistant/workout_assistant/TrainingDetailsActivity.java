package com.example.assistant.workout_assistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assistant.workout_assistant.adapters.ExerciseBeanArrayAdapter;
import com.example.assistant.workout_assistant.exercises.Training;
import com.example.assistant.workout_assistant.R;
import org.w3c.dom.Text;

public class TrainingDetailsActivity extends AppCompatActivity {

    public Training training;

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

    }
}
