package com.example.assistant.workout_assistant.fragments.panels;

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
import com.example.assistant.workout_assistant.PlanTrainingActivity;
import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.database.tables.TrainingsDAO;
import com.example.assistant.workout_assistant.exercises.Training;

public class EditPanelFragment extends Fragment {

    private Training training;
    TrainingsDAO trainingsDAO;
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
        trainingsDAO = new TrainingsDAO(getActivity());
        deleteDialog = createDeletionDialog();
        context = getActivity();
    }

    private DialogInterface.OnClickListener createDeletionDialog() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:

                        boolean result = trainingsDAO.deleteTraining(training.get_id());

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

        final Button planButton = (Button) view.findViewById(R.id.planning_button);

        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlanTrainingActivity.class);
                intent.putExtra("TRAINING", training);
                startActivity(intent);
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
}
