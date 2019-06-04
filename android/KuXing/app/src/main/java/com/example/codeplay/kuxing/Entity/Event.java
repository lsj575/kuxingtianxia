package com.example.codeplay.kuxing.Entity;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private String username = null;
    private String title = null;
    private String content = null;
    private String location = null;
    private Double latitude = null;
    private Double longitude = null;
    private String img = null;

    private Date date;
    public Event() {}
    public Event(String username, String title, String content, String location, Double latitude, Double longitude, String img, Date date){
        this.username = username;
        this.title = title;
        this.content = content;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.img = img;
        this.date = date;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

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

    public String getImg() { return img; }

    public void setImg(String img) { this.img = img; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
