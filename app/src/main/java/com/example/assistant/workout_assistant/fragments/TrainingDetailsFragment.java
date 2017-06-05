package com.example.assistant.workout_assistant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.adapters.ExerciseBeanArrayAdapter;
import com.example.assistant.workout_assistant.exercises.Training;


public class TrainingDetailsFragment extends Fragment {

    private Training training;

    public TrainingDetailsFragment() {

    }

    public static TrainingDetailsFragment newInstance(Training training) {
        TrainingDetailsFragment fragment = new TrainingDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("TRAINING", training);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        training = (Training) getArguments().getSerializable("TRAINING");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_training_details, container, false);

        TextView trainingName = (TextView) view.findViewById(R.id.trainingName);
        trainingName.setText(training.getName());

        TextView authorName = (TextView) view.findViewById(R.id.author);
        authorName.setText(training.getAuthor());

        ListView exercisesList = (ListView) view.findViewById(R.id.exerciseBeansList);
        exercisesList.setAdapter(new ExerciseBeanArrayAdapter(getActivity(), training.getExercises()));

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
