package com.example.assistant.workout_assistant;

import android.support.v4.app.FragmentTransaction;
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
import com.example.assistant.workout_assistant.fragments.DownloadPanelFragment;
import com.example.assistant.workout_assistant.fragments.TrainingDetailsFragment;

public class TrainingDetailsActivity extends AppCompatActivity {

    private Training training;
    private TrainingDetailsFragment detailsFragment;
    private DownloadPanelFragment downloadPanelFragment;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_details);

        Bundle bundle = getIntent().getExtras();

        training = (Training) bundle.getSerializable("TRAINING");

        detailsFragment = TrainingDetailsFragment.newInstance(training);

        getSupportFragmentManager().beginTransaction()
        .replace(R.id.detailsFragment, detailsFragment)
        .commit();

        downloadPanelFragment = DownloadPanelFragment.newInstance(training);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.panelFragment, downloadPanelFragment)
                .commit();


    }
}
