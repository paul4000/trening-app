package com.example.assistant.workout_assistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.assistant.workout_assistant.exercises.Training;
import com.example.assistant.workout_assistant.fragments.panels.DownloadPanelFragment;
import com.example.assistant.workout_assistant.fragments.panels.EditPanelFragment;
import com.example.assistant.workout_assistant.fragments.TrainingDetailsFragment;
import com.example.assistant.workout_assistant.webService.ResponseTrainingsHeader;
import com.example.assistant.workout_assistant.webService.TrainingService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainingDetailsActivity extends AppCompatActivity {

    private Training training;
    private TrainingDetailsFragment detailsFragment;
    private DownloadPanelFragment downloadPanelFragment;
    private EditPanelFragment editPanelFragment;
    private String mode;
    private TrainingService trainingService = new TrainingService();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_details);

        Bundle bundle = getIntent().getExtras();

        mode = bundle.getString("MODE");

        if(mode.equals("WEB")){
            ResponseTrainingsHeader trainingsHeader =
                    (ResponseTrainingsHeader) bundle.getSerializable("TRAINING_HEADER");

            loadTraining(trainingsHeader.get_id());

        } else {
            training = (Training) bundle.getSerializable("TRAINING");
        }


        if (mode.equals("EDIT")) {

            detailsFragment = TrainingDetailsFragment.newInstance(training);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailsFragment, detailsFragment)
                    .commit();

            editPanelFragment = EditPanelFragment.newInstance(training);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.panelFragment, editPanelFragment)
                    .commit();
        }


    }

    private void loadTraining(String trainingId){
        trainingService.loadTraining(new Callback<Training>() {
            @Override
            public void onResponse(Call<Training> call, Response<Training> response) {
                training = response.body();

                detailsFragment = TrainingDetailsFragment.newInstance(training);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detailsFragment, detailsFragment)
                        .commit();

                downloadPanelFragment = DownloadPanelFragment.newInstance(training);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.panelFragment, downloadPanelFragment)
                        .commit();
            }

            @Override
            public void onFailure(Call<Training> call, Throwable t) {
                TextView textView = (TextView) findViewById(R.id.textViewError);
                textView.setText(getString(R.string.error_download_details));
            }
        }, trainingId);
    }
}
