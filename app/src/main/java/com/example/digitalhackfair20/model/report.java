package com.example.digitalhackfair20.model;

public class report {
    private String id;
    private String criminal_id;
    private String victim_id;
    private String detail;

    public report() {

    }

    public report(String id, String criminal_id, String victim_id, String detail) {
        this.id = id;
        this.criminal_id = criminal_id;
        this.victim_id = victim_id;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCriminal_id() {
        return criminal_id;
    }

    public void setCriminal_id(String criminal_id) {
        this.criminal_id = criminal_id;
    }

    public String getVictim_id() {
        return victim_id;
    }

    public void setVictim_id(String victim_id) {
        this.victim_id = victim_id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
