package com.example.codeplay.kuxing.Entity;

import java.util.Date;

public class Event {
    private String title;
    private String content;
    private String location;
    private Date date;
    public Event(){}
    public Event(String title,String content,String location,Date date){
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
