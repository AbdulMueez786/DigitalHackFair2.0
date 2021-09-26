package com.example.digitalhackfair20.model;

public class message {
    
    private String id;
    private String type;
    private String text;
    private String time;
    private String status;
    private String sender_id;
    private String receiver_id;

    public message() {

    }

    public message(String id, String type, String text, String time, String status, String sender_id, String receiver_id) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.time = time;
        this.status = status;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }
}
