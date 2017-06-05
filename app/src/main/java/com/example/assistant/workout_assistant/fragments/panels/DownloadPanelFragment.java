package com.example.assistant.workout_assistant.fragments.panels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.database.tables.TrainingsDAO;
import com.example.assistant.workout_assistant.exercises.Training;

public class DownloadPanelFragment extends Fragment {


    private Training training;
    TrainingsDAO trainingsDAO;

    public DownloadPanelFragment() {
    }

    public static DownloadPanelFragment newInstance(Training training) {
        DownloadPanelFragment fragment = new DownloadPanelFragment();
        Bundle args = new Bundle();
        args.putSerializable("TRAINING", training);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        training = (Training) getArguments().getSerializable("TRAINING");
        trainingsDAO = new TrainingsDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.download_panel_fragment, container, false);
        final Button saveButton = (Button) view.findViewById(R.id.save);

        if(trainingsDAO.trainingExist(training.get_id())) disableButtonIfCannotDownload(saveButton, view);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info;
                boolean downloaded = trainingsDAO.insertTraining(training);
                if(downloaded) info = getString(R.string.success_download);
                else info = getString(R.string.error_download);
                Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();

                if(downloaded) disableButtonIfCannotDownload(saveButton, view);
            }
        });
        return view;

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        trainingsDAO.close();
        super.onDestroy();
    }

    private void disableButtonIfCannotDownload(Button saveButton, View view) {
        saveButton.setEnabled(false);
        TextView alreadySaved = (TextView) view.findViewById(R.id.already_saved);
        alreadySaved.setVisibility(View.VISIBLE);

    }
}
