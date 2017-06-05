package com.example.assistant.workout_assistant;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assistant.workout_assistant.fragments.pickers.DatePickerFragment;
import com.example.assistant.workout_assistant.fragments.pickers.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class PlanTrainingActivity extends FragmentActivity implements TimePickerFragment.TimePickerFragmentListener, DatePickerFragment.DatePickerFragmentListener{


    private TextView displayDate;
    private TextView displayTime;
    private Button saveDate;
    private Button saveTime;
    Calendar calendar;
    FragmentManager fragmentManager;
    TimePickerFragment timePickerFragment;
    DatePickerFragment datePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_training);

        displayDate = (TextView) findViewById(R.id.chosen_date);
        displayTime = (TextView) findViewById(R.id.chosen_time);

        saveDate = (Button) findViewById(R.id.change_date_button);
        saveTime = (Button) findViewById(R.id.change_time_button);

        fragmentManager = getFragmentManager();
        timePickerFragment = TimePickerFragment.newInstance(this);
        datePickerFragment = DatePickerFragment.newInstance(this);

        calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        updateDisplayDate();
        updateDisplayTime();

        saveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerFragment.show(fragmentManager, "timePicker");
            }
        });

        saveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment.show(fragmentManager, "datePicker");
            }
        });

    }


    public void updateDisplayDate(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String formattedDate = dateFormatter.format(calendar.getTime());
        displayDate.setText(formattedDate);
    }

    public void updateDisplayTime(){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String formattedTime = timeFormatter.format(calendar.getTime());
        displayTime.setText(formattedTime);
    }

    @Override
    public void onTimeSet(Calendar c) {
        calendar.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, 0);
        updateDisplayTime();
    }

    @Override
    public void onDateSet(Calendar c) {
        calendar.set(Calendar.YEAR, c.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, c.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        updateDisplayDate();
    }
}
