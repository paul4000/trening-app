package com.example.assistant.workout_assistant.notifications;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.assistant.workout_assistant.R;

import java.util.Date;


public class TrainingNotificationFactory {

  private static String TRAINING_NOW = String.valueOf(R.string.notification_now);
  private static String TRAINING_BEFORE = String.valueOf(R.string.notification_before);


  public static Notification getInstance(Context context, String trainingName, Date date, NotificationType type){

    Notification resultNotification = null;

    switch (type){
        case BEFORE:
          resultNotification =  getBeforeNotification(context, trainingName, date);
          break;
        case NOW:
          resultNotification =  getNowNotification(context, trainingName, date);
          break;
      }
      return resultNotification;
  }

  private static Notification getBeforeNotification(Context context, String trainingName, Date date){
    NotificationCompat.Builder nBuilder =
            new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(trainingName)
                    .setContentText(TRAINING_BEFORE)
                    .setWhen(date.getTime());

    return nBuilder.build();

  }

  private static Notification getNowNotification(Context context, String trainingName, Date date){
    NotificationCompat.Builder nBuilder =
            new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(trainingName)
                    .setContentText(TRAINING_NOW)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setWhen(date.getTime());

    return nBuilder.build();

  }

}
