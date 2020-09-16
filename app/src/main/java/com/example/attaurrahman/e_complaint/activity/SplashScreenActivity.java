package com.example.attaurrahman.e_complaint.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.genralUtils.AppRepository;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppRepository.isLoggedIn(SplashScreenActivity.this)) {
                    finish();
                    startActivity(new Intent(SplashScreenActivity.this, DrawerActivity.class));
                } else {
                    finish();
                    startActivity(new Intent(SplashScreenActivity.this, LoginSignUpActivity.class));
                }
                finish();
            }
        }, 1000);
    }

    }
