package com.example.assistant.workout_assistant.bo;

import java.io.Serializable;


public class SeriesBean implements Serializable {
    /**
     * time : 20
     * quantity : 20
     * _id : 5910d04fea94d232b3f36eb1
     */

    private int time;
    private int quantity;
    private int load;
    private String _id;

    public SeriesBean(int time, int quantity, int load, String _id) {
        this.time = time;
        this.quantity = quantity;
        this.load = load;
        this._id = _id;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

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
                (time > 0 ? "time: " + time + " ": "") +
                (quantity > 0 ? "quantity: " + quantity + " ":"") +
                (load > 0 ? "load: " + load + " ":"");
    }
}
