package com.example.mickaelly.ada_ava.model;

public class User {

    private long id;
    private String name;
    private int registration;
    private String email;
    private String password;
    private String typeUser;

    public User() {
    }

    public User(String name, String email, String password, String typeUser) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.typeUser = typeUser;
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

    public int getRegistration() {
        return registration;
    }

    public void setRegistration(int registration) {
        this.registration = registration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }
}
