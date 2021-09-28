package com.example.mickaelly.ada_ava.model;

import java.io.Serializable;

public class Course implements Serializable {

    private long id;
    private String name;
    private String level;

    public Course() {}

    public Course(String name, String level) {
        this.name = name;
        this.level = level;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
