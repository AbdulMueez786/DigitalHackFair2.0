package com.example.digitalhackfair20.model;

public class Group {
    private String id;
    private String name;
    private String latest_message_text;
    private String latest_message_time;
    private String description;
    private String group_pic;

    public Group() {

    }

    public Group(String id, String name, String latest_message_text, String latest_message_time, String description, String group_pic) {
        this.id = id;
        this.name = name;
        this.latest_message_text = latest_message_text;
        this.latest_message_time = latest_message_time;
        this.description = description;
        this.group_pic = group_pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatest_message_text() {
        return latest_message_text;
    }

    public void setLatest_message_text(String latest_message_text) {
        this.latest_message_text = latest_message_text;
    }

    public String getLatest_message_time() {
        return latest_message_time;
    }

    public void setLatest_message_time(String latest_message_time) {
        this.latest_message_time = latest_message_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup_pic() {
        return group_pic;
    }

    public void setGroup_pic(String group_pic) {
        this.group_pic = group_pic;
    }
}
