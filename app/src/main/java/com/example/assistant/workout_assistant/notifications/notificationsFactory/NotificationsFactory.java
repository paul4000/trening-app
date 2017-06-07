package com.example.assistant.workout_assistant.notifications.notificationsFactory;

import android.app.Notification;
import android.content.Context;

public interface NotificationsFactory {
    Notification getInstance(Context context, String trainingName);
}
