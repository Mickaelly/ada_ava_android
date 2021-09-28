package com.example.mickaelly.ada_ava.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Shared preferences manager.
 */
public class Prefs {
    private static final String SERVER_IP = "ada.serverIp";
    private static final String SERVER_PORT = "ada.serverPort";

    private final SharedPreferences sharedPrefs;

    public Prefs(final Context context) {
        sharedPrefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public String getServerIP() {
        return sharedPrefs.getString(SERVER_IP, "");
    }

    public String getServerPort() {
        return sharedPrefs.getString(SERVER_PORT, "");
    }

    public void setServerIP(final String ip) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(SERVER_IP, ip);
        editor.apply();
    }

    public void setServerPort(final String port) {
        final SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(SERVER_PORT, port);
        editor.apply();
    }
}