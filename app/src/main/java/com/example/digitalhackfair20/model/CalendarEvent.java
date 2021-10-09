package com.example.digitalhackfair20.model;

import java.util.Date;


public class CalendarEvent {

    private String id;
    private String name;
    private String description;
    private Date timeAndDate;
    private int colorCode;

    public CalendarEvent() {

    }

    public CalendarEvent(String id, String name, String description, Date timeAndDate, int colorCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timeAndDate = timeAndDate;
        this.colorCode = colorCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(Date timeAndDate) {
        this.timeAndDate = timeAndDate;
    }
}
