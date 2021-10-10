package com.example.digitalhackfair20.model;

public class report {

    private String id;
    private String criminal_id;
    private String criminal_name;
    private String victim_id;
    private String victim_name;
    private String detail;
    private String profile;

    public report() {

    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCriminal_name() {
        return criminal_name;
    }

    public void setCriminal_name(String criminal_name) {
        this.criminal_name = criminal_name;
    }

    public String getVictim_name() {
        return victim_name;
    }

    public void setVictim_name(String victim_name) {
        this.victim_name = victim_name;
    }

    public report(String id, String profile, String criminal_name, String victim_name, String criminal_id, String victim_id, String detail) {
        this.id = id;
        this.profile = profile;
        this.criminal_name = criminal_name;
        this.victim_name = victim_name;
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
