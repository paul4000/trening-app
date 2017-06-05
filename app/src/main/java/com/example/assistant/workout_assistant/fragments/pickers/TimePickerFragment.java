package com.example.assistant.workout_assistant.fragments.pickers;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TimePickerFragmentListener timePickerListener;

    public static TimePickerFragment newInstance(TimePickerFragmentListener listener) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setTimePickerFragmentListener(listener);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = new GregorianCalendar();
//        c.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        notifyTimePickerFragmentListener(c);
    }

    public interface TimePickerFragmentListener {
        void onTimeSet(Calendar calendar);
    }

    public TimePickerFragmentListener getDatePickerListener() {
        return this.timePickerListener;
    }

    public void setTimePickerFragmentListener(TimePickerFragmentListener listener) {
        this.timePickerListener = listener;
    }
    protected void notifyTimePickerFragmentListener(Calendar calendar) {
        if(this.timePickerListener != null) {
            this.timePickerListener.onTimeSet(calendar);
        }
    }



}
