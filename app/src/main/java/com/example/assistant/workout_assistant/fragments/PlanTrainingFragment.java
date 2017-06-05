package com.example.assistant.workout_assistant.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.exercises.Training;

public class PlanTrainingFragment extends Fragment {

    private Training training;

    public PlanTrainingFragment() {
    }

    public static PlanTrainingFragment newInstance(Training training) {
        PlanTrainingFragment fragment = new PlanTrainingFragment();
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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
////        TextView textView = new TextView(getActivity());
////        textView.setText(R.string.hello_blank_fragment);
////        return textView;
////    }




}
