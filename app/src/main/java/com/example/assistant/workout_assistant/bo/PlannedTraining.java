package com.example.assistant.workout_assistant.bo;


public class PlannedTraining {

    private String date;
    private int id;
    private String trainingId;
    private String trainingName;
    private int beforeNotificationId;
    private int nowNotificationId;

    public PlannedTraining(int id, String trainingId, String trainingName, String date, int beforeNotificationId, int nowNotificationId) {
        this.id = id;
        this.trainingId = trainingId;
        this.trainingName = trainingName;
        this.date = date;
        this.beforeNotificationId = beforeNotificationId;
        this.nowNotificationId = nowNotificationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public int getBeforeNotificationId() {
        return beforeNotificationId;
    }

    public void setBeforeNotificationId(int beforeNotificationId) {
        this.beforeNotificationId = beforeNotificationId;
    }

    public int getNowNotificationId() {
        return nowNotificationId;
    }

    public void setNowNotificationId(int nowNotificationId) {
        this.nowNotificationId = nowNotificationId;
    }
}
