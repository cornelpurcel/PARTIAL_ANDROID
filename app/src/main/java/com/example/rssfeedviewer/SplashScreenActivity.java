package com.example.rssfeedviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainActivityIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);

    }
}