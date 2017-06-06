package com.example.assistant.workout_assistant.notifications;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.assistant.workout_assistant.R;


public class TrainingNotificationFactory {

  public static Notification getInstance(Context context, String trainingName, int type){

    Notification resultNotification = null;

    switch (type){
        case 1:
          resultNotification =  getBeforeNotification(context, trainingName);
          break;
        case 2:
          resultNotification =  getNowNotification(context, trainingName);
          break;
      }
      return resultNotification;
  }

  private static Notification getBeforeNotification(Context context, String trainingName){
    NotificationCompat.Builder nBuilder =
            new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(trainingName)
                    .setContentText(context.getString(R.string.notification_before));

    return nBuilder.build();

  }

  private static Notification getNowNotification(Context context, String trainingName){
    NotificationCompat.Builder nBuilder =
            new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(trainingName)
                    .setContentText(context.getString(R.string.notification_now))
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

    return nBuilder.build();

  }

}
