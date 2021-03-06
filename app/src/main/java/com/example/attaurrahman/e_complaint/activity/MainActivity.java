package com.example.attaurrahman.e_complaint.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.fragment.Complaint_Fragment;
import com.example.attaurrahman.e_complaint.fragment.LoginTabFragment;
import com.example.attaurrahman.e_complaint.genralUtils.Utilities;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.attaurrahman.e_complaint.configuration.Config.BASE_URL;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    CallbackManager callbackManager;
    Boolean flagLogin, flagIsLogin;
    String personFullname, personEmail, personUsername, personGender, provider;
    String email, first_name, last_name, name, gender,dilogEmail;
    public static final int PESON = 0;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        flagIsLogin = false;
        flagLogin = true;



        sharedPreferences = getSharedPreferences("com.ecomp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //Toast.makeText(this, deviceId, Toast.LENGTH_SHORT).show();
        editor.putString("android_id",deviceId).commit();

      if (sharedPreferences.getString("isLogin","").equals("true")){
          Fragment fragment =  new Complaint_Fragment();
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
      }else {
          Fragment fragment =  new LoginTabFragment();
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
      }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displayuserInfo(JSONObject object) {

        try {

            name = object.getString("name");
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            gender = object.getString("gender");


        } catch (JSONException e) {
            e.printStackTrace();


        }


        ;


    }

    private void registerSocialApiCall() {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, BASE_URL + "sociallogin",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("200")) {

                            Toast.makeText(MainActivity.this, "Succesfull", Toast.LENGTH_SHORT).show();
                            Log.d("zama api response", response.toString());
                            Utilities.connectFragmentForMyComplaint(MainActivity.this, new Complaint_Fragment());
                            editor.putString("isLogin","true").commit();
                        } else if (response.contains("202")) {
                            Toast.makeText(MainActivity.this, "Already Registerd", Toast.LENGTH_SHORT).show();
                            Utilities.connectFragmentForMyComplaint(MainActivity.this, new Complaint_Fragment());
                            editor.putString("isLogin","true").commit();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        Log.d("error", error.toString());

                        NetworkResponse networkResponse = error.networkResponse;

                        String errorMessage = "Unknown error";
                        if (networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                errorMessage = "Request timeout";

                            } else if (error.getClass().equals(NoConnectionError.class)) {

                                errorMessage = "Failed to connect server";
                            }
                        } else {
                            String result = new String(networkResponse.data);
                            try {
                                JSONObject response = new JSONObject(result);
                                String status = response.getString("status");
                                String message = response.getString("message");

                                Log.e("Error Status", status);
                                Log.e("Error Message", message);

                                if (networkResponse.statusCode == 404) {
                                    errorMessage = "Resource not found";


                                } else if (networkResponse.statusCode == 401) {
                                    errorMessage = message + " Please login again";


                                } else if (networkResponse.statusCode == 400) {
                                    errorMessage = message + " Check your inputs";


                                } else if (networkResponse.statusCode == 500) {
                                    errorMessage = message + " Something is getting wrong";

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("Error", errorMessage);
                        error.printStackTrace();
                    }
                }
        ) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("fullname", first_name+last_name);
                params.put("provider", "facebook");
                params.put("provider_id", sharedPreferences.getString("android_id",""));
                params.put("name", name);
                params.put("device_type", "android");
                params.put("device_token", "12345");
                params.put("email", email+dilogEmail);
                params.put("gender", gender);


                return params;
            }
        };
        Volley.newRequestQueue(MainActivity.this).add(postRequest);

    }


}
