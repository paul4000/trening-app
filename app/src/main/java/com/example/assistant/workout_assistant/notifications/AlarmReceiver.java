package com.example.assistant.workout_assistant.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        int type =   bundle.getInt("NOTI_TYPE");

        String trainingName = bundle.getString("TRAINING_NAME");
        int notificationId = bundle.getInt("NOTI_ID");

        Notification notification = TrainingNotificationProducer.createNotification(context, trainingName, type);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notification);
    }
}
