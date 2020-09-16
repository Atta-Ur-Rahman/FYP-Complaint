package com.example.attaurrahman.e_complaint.genralUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppRepository {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String mConfig = "com.example.attaurrahman.e_complaint";


    public static SharedPreferences mGetValue(Context context) {
        sharedPreferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static SharedPreferences.Editor mPutValue(Context context) {
        sharedPreferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
        return editor = sharedPreferences.edit();
    }


    public static String mUserName(Context context) {
        return String.valueOf(mGetValue(context).getString("mUserName", null));
    }


    public static String mUserVerifyEmail(Context context) {
        return String.valueOf(mGetValue(context).getString("verify_email", null));
    }


    public static String mUserId(Context context) {
        return mGetValue(context).getString("mUserId", null);
    }


    public static String mEmail(Context context) {
        return String.valueOf(mGetValue(context).getString("mUserEmail", null));
    }


    public static boolean isLoggedIn(Context context) {
        return mGetValue(context).getBoolean("loggedIn", false);

    }
}