package com.example.project_main;

import android.graphics.Bitmap;

public class TimelineSelectDto {

    private String nickname;
    private String context;
    private String date;
    private byte[] alarm_ing;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public byte[] getAlarm_ing() {
        return alarm_ing;
    }

    public void setAlarm_ing(byte[] alarm_ing) {
        this.alarm_ing = alarm_ing;
    }
}
