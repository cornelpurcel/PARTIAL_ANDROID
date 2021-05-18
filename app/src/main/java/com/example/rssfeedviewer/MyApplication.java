package com.example.rssfeedviewer;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MyApplication extends Application {

    private String username;
    private List<String> usernameList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        usernameList = new ArrayList<String>();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {

            SharedPreferences usersPref = getSharedPreferences("users", 0);
            SharedPreferences.Editor userEditor = usersPref.edit();

            userEditor.putBoolean("Default", true);

            userEditor.commit();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        setUser();
    }

    private void setUser() {
        SharedPreferences usersPref = getSharedPreferences("users", 0);
        Map<String, ?> allEntries = usersPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue().toString().equals("true")){
                username = entry.getKey();
                break;
            }
        }
    }
}
