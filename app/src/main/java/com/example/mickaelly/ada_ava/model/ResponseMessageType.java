package com.example.mickaelly.ada_ava.model;

import okhttp3.OkHttpClient;

public class ResponseMessageType {

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    private boolean isLogin;
    private OkHttpClient client;
}
