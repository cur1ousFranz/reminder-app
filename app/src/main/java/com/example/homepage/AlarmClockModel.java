package com.example.homepage;

public class AlarmClockModel {

    private int clockId;
    private int clockHour;
    private int clockMinute;

    public AlarmClockModel(int clockId, int clockHour, int clockMinute) {
        this.clockId = clockId;
        this.clockHour = clockHour;
        this.clockMinute = clockMinute;
    }

    public int getClockHour() {
        return clockHour;
    }

    public void setClockHour(int clockHour) {
        this.clockHour = clockHour;
    }

    public int getClockMinute() {
        return clockMinute;
    }

    public void setClockMinute(int clockMinute) {
        this.clockMinute = clockMinute;
    }

    public int getClockId() {
        return clockId;
    }

    public void setClockId(int clockId) {
        this.clockId = clockId;
    }

    @Override
    public String toString() {

        if (clockHour < 10 && clockMinute < 10){
            return "0" + clockHour + ":" + "0" + clockMinute;
        }else if (clockHour < 10){
            return "0" + clockHour + ":" + clockMinute;
        }else if (clockMinute < 10){
            return  clockHour + ":" + "0" + clockMinute;
        } else
            return clockHour + ":" + clockMinute;
    }
}
