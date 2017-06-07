package com.example.assistant.workout_assistant.bo;

import java.io.Serializable;
import java.util.List;

public class Training implements Serializable{

    /**
     * _id : 5910d04fea94d232b3f36eae
     * authorName : Kamil GryboĹ
     * name : Wszyscy zginiemy
     * __v : 0
     * exercises : [{"exercise":{"_id":"590cb72ed522013f68155945","description":"Podnoszenie ciÄ\u0099Ĺźaru na sztandze","name":"Wyciskanie sztangi","place":"indoor","__v":0,"requirements":["sprzÄ\u0099t"],"muscles":["rÄ\u0099ce","klatka piersiowa"]},"time":true,"quantity":false,"load":true,"_id":"5910d04fea94d232b3f36eaf","series":[{"time":20,"quantity":20,"_id":"5910d04fea94d232b3f36eb1"},{"time":20,"quantity":15,"_id":"5910d04fea94d232b3f36eb0"}]}]
     * updated : 2017-05-08T20:08:47.616Z
     */

    private String _id;
    private String authorName;
    private Author author;
    private String name;
    private int __v;
    private String updated;
    private List<ExercisesBean> exercises;

    public Training(String _id, String authorName, String name, int __v, String updated, List<ExercisesBean> exercises) {
        this._id = _id;
        this.authorName = authorName;
        this.name = name;
        this.__v = __v;
        this.author = new Author();
        this.author.setUsername(this.authorName);
        this.updated = updated;
        this.exercises = exercises;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthorName() {
        return author.getUsername();
    }

    public void setAuthorName(String authorName) {
        this.author.setUsername(authorName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public List<ExercisesBean> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExercisesBean> exercises) {
        this.exercises = exercises;
    }

    public static class Author implements Serializable{

        /**
         * _id : 593735873e197c0011737cc1
         * username : Kamil
         */

        private String _id;
        private String username;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public static class ExercisesBean implements Serializable {
        /**
         * exercise : {"_id":"590cb72ed522013f68155945","description":"Podnoszenie ciÄ\u0099Ĺźaru na sztandze","name":"Wyciskanie sztangi","place":"indoor","__v":0,"requirements":["sprzÄ\u0099t"],"muscles":["rÄ\u0099ce","klatka piersiowa"]}
         * time : true
         * quantity : false
         * load : true
         * _id : 5910d04fea94d232b3f36eaf
         * series : [{"time":20,"quantity":20,"_id":"5910d04fea94d232b3f36eb1"},{"time":20,"quantity":15,"_id":"5910d04fea94d232b3f36eb0"}]
         */

        private Exercise exercise;
        private boolean time;
        private boolean quantity;
        private boolean load;
        private String _id;
        private List<SeriesBean> series;

        public ExercisesBean(Exercise exercise, boolean time, boolean quantity, boolean load, String _id, List<SeriesBean> series) {
            this.exercise = exercise;
            this.time = time;
            this.quantity = quantity;
            this.load = load;
            this._id = _id;
            this.series = series;
        }

        public Exercise getExercise() {
            return exercise;
        }

        public void setExercise(Exercise exercise) {
            this.exercise = exercise;
        }

        public boolean isTime() {
            return time;
        }

        public void setTime(boolean time) {
            this.time = time;
        }

        public boolean isQuantity() {
            return quantity;
        }

        public void setQuantity(boolean quantity) {
            this.quantity = quantity;
        }

        public boolean isLoad() {
            return load;
        }

        public void setLoad(boolean load) {
            this.load = load;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public List<SeriesBean> getSeries() {
            return series;
        }

        public void setSeries(List<SeriesBean> series) {
            this.series = series;
        }


    }
}
