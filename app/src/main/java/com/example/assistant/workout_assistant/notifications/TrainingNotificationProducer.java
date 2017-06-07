package com.example.assistant.workout_assistant.notifications;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.notifications.notificationsFactory.BeforeNotificationsFactory;
import com.example.assistant.workout_assistant.notifications.notificationsFactory.NotificationsFactory;
import com.example.assistant.workout_assistant.notifications.notificationsFactory.NowNotificationsFactory;


public class TrainingNotificationProducer {

  public static Notification createNotification(Context context, String trainingName, int type){

      NotificationsFactory notificationsFactory = null;

    switch (type) {
        case 1:
            notificationsFactory = new BeforeNotificationsFactory();
            break;
        case 2:
            notificationsFactory = new NowNotificationsFactory();
            break;
    }

      if(notificationsFactory == null) throw new IllegalArgumentException();

      return notificationsFactory.getInstance(context, trainingName);
  }

}
