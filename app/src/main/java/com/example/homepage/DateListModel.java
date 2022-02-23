package com.example.homepage;

public class DateListModel {

    private int data_id;
    private String date_name;
    private String date_text;

    public DateListModel(int data_id, String date_name, String date_text) {
        this.data_id = data_id;
        this.date_text = date_text;
        this.date_name = date_name;
    }

    public String getDate_name() {
        return date_name;
    }

    public void setDate_name(String date_name) {
        this.date_name = date_name;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
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
