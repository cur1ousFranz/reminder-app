package com.example.homepage;

public class CalendarListModel {

    private int date_id;
    private String date_name;
    private String date_text;

    public CalendarListModel(int data_id, String date_name, String date_text) {
        this.date_id = data_id;
        this.date_text = date_text;
        this.date_name = date_name;
    }

    public String getDate_name() {
        return date_name;
    }

    public void setDate_name(String date_name) {
        this.date_name = date_name;
    }

    public int getDate_id() {
        return date_id;
    }

    public void setDate_id(int date_id) {
        this.date_id = date_id;
    }

    public String getDate_text() {
        return date_text;
    }

    public void setDate_text(String date_text) {
        this.date_text = date_text;
    }

    @Override
    public String toString() {
        return date_name + ": " + date_text;
    }
}
