package com.example.assistant.workout_assistant.notifications.notificationsFactory;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.assistant.workout_assistant.R;

public class NowNotificationsFactory implements NotificationsFactory {

    @Override
    public Notification getInstance(Context context, String trainingName) {
        NotificationCompat.Builder nBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(trainingName)
                        .setContentText(context.getString(R.string.notification_now))
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        return nBuilder.build();
    }
}
