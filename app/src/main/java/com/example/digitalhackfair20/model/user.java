package com.example.digitalhackfair20.model;

public class user {

    private String id;
    private String app_id;
    private String name;
    private String email;
    private String post;
    private String bio;
    private String join_date;
    private String gender;
    private String status;
    private String user_profile;
    private String warning;
    private String account_suspended;

    public user() {

    }

    public user(String id, String app_id, String name, String email, String post, String bio, String join_date, String gender, String status, String user_profile, String warning, String account_suspended) {
        this.id = id;
        this.app_id = app_id;
        this.name = name;
        this.email = email;
        this.post = post;
        this.bio = bio;
        this.join_date = join_date;
        this.gender = gender;
        this.status = status;
        this.user_profile = user_profile;
        this.warning = warning;
        this.account_suspended = account_suspended;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getAccount_suspended() {
        return account_suspended;
    }

    public void setAccount_suspended(String account_suspended) {
        this.account_suspended = account_suspended;
    }
}
