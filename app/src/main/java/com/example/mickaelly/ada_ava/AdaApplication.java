package com.example.mickaelly.ada_ava;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.mickaelly.ada_ava.activities.HomeActivity;
import com.example.mickaelly.ada_ava.activities.LoginActivity;
import com.example.mickaelly.ada_ava.model.ResponseMessageType;
import com.example.mickaelly.ada_ava.model.User;
import com.example.mickaelly.ada_ava.preferences.Prefs;

public class AdaApplication extends Application {

    private static AdaApplication appInstance;
    private static String TAG = "Ada/Application";
    private static User userSession;
    private static ResponseMessageType responseMessageType;
    private Prefs preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        Log.i(TAG, "Application started");
        preferences = new Prefs(this.getApplicationContext());
    }

    public static AdaApplication getInstance() {
        return appInstance;
    }

    public Prefs getPreferences() {
        return preferences;
    }

    public ResponseMessageType getResponseMessageType() {
        return this.responseMessageType;
    }

    public void setUserSession(User user) {
        this.userSession = user;
    }

    public User getUserSession() {
        return this.userSession;
    }
}
