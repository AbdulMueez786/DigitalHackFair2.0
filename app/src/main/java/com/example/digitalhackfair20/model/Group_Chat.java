package com.example.digitalhackfair20.model;

public class Group_Chat {

    private String sender;
    private String receiver;
    private String message;
    private String Message_Type;
    private String Message_id;
    private String Time;
    private String Status;

    public Group_Chat(String sender, String reciever, String message, String Message_Type, String Message_Id, String Time, String Status) {
        this.sender = sender;
        this.receiver = reciever;
        this.message = message;
        this.Message_Type = Message_Type;
        this.Message_id = Message_Id;
        this.Time = Time;
        this.Status = Status;
    }

    public Group_Chat() {
        this.sender = "";
        this.receiver = "";
        this.message = "";
        this.Message_Type = "";
        this.Time = "";
        this.Status = "";
    }

    public String getMessage_id() {
        return Message_id;
    }

    public void setMessage_id(String message_id) {
        Message_id = message_id;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage_Type() {
        return Message_Type;
    }

    public void setMessage_Type(String message_Type) {
        Message_Type = message_Type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
