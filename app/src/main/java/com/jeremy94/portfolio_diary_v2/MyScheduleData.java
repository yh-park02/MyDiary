package com.jeremy94.portfolio_diary_v2;

import java.io.Serializable;

//intent에 put하기 위해서는 Serializable를 구현
public class MyScheduleData implements Serializable {

    private String title;
    private String date;
    private String time;
    private String position;
    private String memo;

    public MyScheduleData(String title, String date, String time, String position, String memo) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.position = position;
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    //MyScheduleData를 문자로 출력시 호출
    @Override
    public String toString(){
        return String.format("• %s\n %s", title, time);
    }
}
