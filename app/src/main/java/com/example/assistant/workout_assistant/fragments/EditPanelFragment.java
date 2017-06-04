package com.example.assistant.workout_assistant.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.assistant.workout_assistant.MyTrainingsActivity;
import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.database.DBHelper;
import com.example.assistant.workout_assistant.exercises.Training;

public class EditPanelFragment extends Fragment {

    private Training training;
    DBHelper dbHelper;
    DialogInterface.OnClickListener deleteDialog;
    Context context;


    public EditPanelFragment() {

    }

    public static EditPanelFragment newInstance(Training training) {
        EditPanelFragment fragment = new EditPanelFragment();
        Bundle args = new Bundle();
        args.putSerializable("TRAINING", training);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        training = (Training) getArguments().getSerializable("TRAINING");
        dbHelper = new DBHelper(getActivity());
        deleteDialog = createDeletionDialog();
        context = getActivity();
    }

    private DialogInterface.OnClickListener createDeletionDialog() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:

                        boolean result = dbHelper.deleteTraining(training.get_id());

                        String info;
                        if(result) info = getString(R.string.success_deleting);
                        else info = getString(R.string.error_deleting);
                        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                        getActivity().finish();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    private void backToTrainingsList() {
        Intent intent = new Intent(context, MyTrainingsActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_panel, container, false);

        final Button deleteButton = (Button) view.findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, deleteDialog)
                        .setNegativeButton(R.string.no, deleteDialog)
                        .show();
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
        dbHelper.close();
        super.onDestroy();
    }
}
