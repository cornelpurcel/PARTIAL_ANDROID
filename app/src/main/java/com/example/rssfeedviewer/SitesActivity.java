package com.example.rssfeedviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SitesActivity extends PreferenceActivity {

    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String username = ((MyApplication) this.getApplication()).getUsername();
        getPreferenceManager().setSharedPreferencesName("sites_pref_" + username);
        addPreferencesFromResource(R.xml.sites_pref);

    }
}