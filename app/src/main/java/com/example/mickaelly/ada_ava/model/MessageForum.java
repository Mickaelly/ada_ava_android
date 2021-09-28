package com.example.mickaelly.ada_ava.model;

import java.util.Date;

public class MessageForum {

    private long id;
    private String nameUser;
    private String date;
    private String message;
    private String theme;

    public void MessageForum(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String name) {
        this.nameUser = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
