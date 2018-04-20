package com.ahmetgokhan.unicity.activities.Login;


import android.content.Intent;

import android.os.ConditionVariable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Homepage.HomeActivity;
import com.ahmetgokhan.unicity.activities.Login.LoginActivity;
import com.ahmetgokhan.unicity.activities.Profile.ProfileActivity;
import com.ahmetgokhan.unicity.config.Config;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                
                if (getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getBoolean(Config.LOGGING_STATUS, false)) {
                    if (getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getBoolean(Config.PROFILE_STATUS, false)) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        }, 2000);
    }
}
