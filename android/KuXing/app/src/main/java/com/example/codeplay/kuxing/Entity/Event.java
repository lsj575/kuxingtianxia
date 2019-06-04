package com.example.codeplay.kuxing.Entity;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Event implements Serializable {
    private String username;
    private String title;
    private String content;
    private String location;
    private Date date;
    private ArrayList<Bitmap> bitmaps;
    public Event(){}
    public Event(String username, String title, String content, String location, Date date, ArrayList<Bitmap> bitmaps){
        this.username = username;
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;
        this.bitmaps = bitmaps;
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

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
