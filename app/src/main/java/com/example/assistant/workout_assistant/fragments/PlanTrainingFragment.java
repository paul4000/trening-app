package com.example.assistant.workout_assistant.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Calendar;

public class PlanTrainingFragment extends Fragment {

    private String trainingName;
    private String trainingId;
    private Calendar calendar;

    public PlanTrainingFragment() {
    }

    public static PlanTrainingFragment newInstance(String trainingName, String trainingId, Calendar calendar) {
        PlanTrainingFragment fragment = new PlanTrainingFragment();
        Bundle args = new Bundle();
        args.putString("TRAINING_NAME", trainingName);
        args.putString("TRAINING_ID", trainingId);
        args.putSerializable("CALENDAR", calendar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trainingName = getArguments().getString("TRAINING_NAME");
        trainingId = getArguments().getString("TRAINING_ID");
        calendar = (Calendar) getArguments().getSerializable("CALENDAR");
    }


}
