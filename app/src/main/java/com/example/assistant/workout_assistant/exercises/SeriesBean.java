package com.example.assistant.workout_assistant.exercises;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Paulina on 31.05.2017.
 */

public class SeriesBean implements Serializable {
    /**
     * time : 20
     * quantity : 20
     * _id : 5910d04fea94d232b3f36eb1
     */

    private int time;
    private int quantity;
    private String _id;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Serie: " +
                "time=" + time +
                ", quantity=" + quantity;
    }
}
