package com.example.mickaelly.ada_ava.model;

import java.io.Serializable;

public class Classroom implements Serializable {

    private long id;
    private String name;
    private long idCourse;
    private String url;

    public Classroom() {
    }

    public Classroom(String name, long idCourse, String url) {
        this.name = name;
        this.idCourse = idCourse;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(long idCourse) {
        this.idCourse = idCourse;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
