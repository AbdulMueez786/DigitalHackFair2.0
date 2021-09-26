package com.example.digitalhackfair20.model;

public class task {
    private String id;
    private String title;
    private String description;
    private String assigned_date;
    private String due_date;

    public task() {

    }

    public task(String id, String title, String description, String assigned_date, String due_date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assigned_date = assigned_date;
        this.due_date = due_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(String assigned_date) {
        this.assigned_date = assigned_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
