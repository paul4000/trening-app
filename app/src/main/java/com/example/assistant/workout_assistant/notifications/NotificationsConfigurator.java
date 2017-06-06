package com.example.assistant.workout_assistant.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;


public class NotificationsConfigurator {
    AlarmManager alarmManager;
    Context context;

    public NotificationsConfigurator(Context context){
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.context = context;
    }

    public void setNotification(Calendar calendar, int type, int notificationId, String trainingName){
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);

        alarmIntent.putExtra("NOTI_TYPE", type);
        alarmIntent.putExtra("TRAINING_NAME", trainingName);
        alarmIntent.putExtra("NOTI_ID", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, alarmIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelNotification(Context context, int notificationId){
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, alarmIntent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
