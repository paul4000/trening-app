package com.example.assistant.workout_assistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.assistant.workout_assistant.exercises.Training;
import com.example.assistant.workout_assistant.fragments.panels.DownloadPanelFragment;
import com.example.assistant.workout_assistant.fragments.panels.EditPanelFragment;
import com.example.assistant.workout_assistant.fragments.TrainingDetailsFragment;

public class TrainingDetailsActivity extends AppCompatActivity {

    private Training training;
    private TrainingDetailsFragment detailsFragment;
    private DownloadPanelFragment downloadPanelFragment;
    private EditPanelFragment editPanelFragment;
    private String mode;

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
        mode = bundle.getString("MODE");

        detailsFragment = TrainingDetailsFragment.newInstance(training);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailsFragment, detailsFragment)
                .commit();


        if (mode.equals("WEB")) {

            downloadPanelFragment = DownloadPanelFragment.newInstance(training);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.panelFragment, downloadPanelFragment)
                    .commit();
        } else {

            editPanelFragment = EditPanelFragment.newInstance(training);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.panelFragment, editPanelFragment)
                    .commit();
        }


    }
}
