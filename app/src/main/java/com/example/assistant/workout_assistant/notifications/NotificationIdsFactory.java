package com.example.assistant.workout_assistant.notifications;

import java.util.Date;


public class NotificationIdsFactory {

    public static int getInstance(){
        return (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
    }
}
