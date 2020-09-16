package com.example.attaurrahman.e_complaint.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.fragment.Complaint_Fragment;
import com.example.attaurrahman.e_complaint.fragment.LoginTabFragment;
import com.example.attaurrahman.e_complaint.genralUtils.Utilities;

public class LoginSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        Utilities.connectFragmentWithOutBackStack(this, new LoginTabFragment());

    }
}