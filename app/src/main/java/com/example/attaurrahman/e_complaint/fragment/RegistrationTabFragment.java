package com.example.attaurrahman.e_complaint.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.activity.DrawerActivity;
import com.example.attaurrahman.e_complaint.configuration.CheckNetwork;
import com.example.attaurrahman.e_complaint.dataModel.signUpModel.SignUpResponse;
import com.example.attaurrahman.e_complaint.genralUtils.AppRepository;
import com.example.attaurrahman.e_complaint.genralUtils.Utilities;
import com.example.attaurrahman.e_complaint.networking.BaseNetworking;
import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationTabFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.M)

    EditText et_registration_fullname, et_registration_email, et_registration_password, et_registration_retypePassword;
    String strFullName, strEmail, strPassword, strRetypePassword;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    View parentView;
    AlertDialog alertDialogSpot;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_registration, container, false);
        et_registration_fullname = Utilities.editTextDeclaration(R.id.et_register_fullname, parentView);
        et_registration_email = Utilities.editTextDeclaration(R.id.et_registration_email, parentView);
        et_registration_password = Utilities.editTextDeclaration(R.id.et_registration_password, parentView);
        et_registration_retypePassword = Utilities.editTextDeclaration(R.id.et_registration_retypePassword, parentView);
        alertDialogSpot = new SpotsDialog(getActivity(), R.style.CustomLoading);


        sharedPreferences = getActivity().getSharedPreferences("com.eComplaint", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        //Utilities.textViewDeclaration(R.id.tv_register, view).setTextAppearance(R.style.boldText);
        Utilities.textViewDeclaration(R.id.tv_login, parentView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.connectFragment(getActivity(), new LoginTabFragment());
            }
        });
        Utilities.buttonDeclaration(R.id.btn_register, parentView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CheckNetwork.isInternetAvailable(getActivity())) {


                    new CDialog(getActivity()).createAlert("No Internet Connection",
                            CDConstants.WARNING,   // Type of dialog
                            CDConstants.LARGE)    //  size of dialog
                            .setAnimation(CDConstants.SCALE_TO_TOP_FROM_BOTTOM)     //  Animation for enter/exit
                            .setDuration(5000)   // in milliseconds
                            .setTextSize(CDConstants.LARGE_TEXT_SIZE)  // CDConstants.LARGE_TEXT_SIZE, CDConstants.NORMAL_TEXT_SIZE
                            .show();
                } else {

                    String strNewPassword = et_registration_password.getText().toString();
                    String strConforimPassword = et_registration_retypePassword.getText().toString();
                    if (et_registration_fullname.getText().length() <= 2) {
                        et_registration_fullname.setError("Enter Full Name");

                    } else if (et_registration_email.getText().length() <= 0) {
                        et_registration_email.setError("Enter Email");

                    } else if (!Utilities.isValidEmail(et_registration_email.getText())) {
                        et_registration_email.setError("Correct Format Email");
                    } else if (strNewPassword.length() <= 6) {
                        et_registration_password.setError("Please more then 6 digit password");

                    } else if (strNewPassword.equals(strConforimPassword)) {
                        registerApiCall();

                    } else et_registration_retypePassword.setError("Password does'nt match ");
                }

            }
        });


        et_registration_retypePassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String strNewPassword = et_registration_password.getText().toString();
                    String strConforimPassword = et_registration_retypePassword.getText().toString();
                    if (et_registration_fullname.getText().length() <= 2) {
                        et_registration_fullname.setError("Enter Full Name");

                    } else if (et_registration_email.getText().length() <= 0) {
                        et_registration_email.setError("Enter Email");

                    } else if (!Utilities.isValidEmail(et_registration_email.getText())) {
                        et_registration_email.setError("Correct Format Email");
                    } else if (strNewPassword.length() >= 6) {
                        et_registration_password.setError("Please more then 6 digit password");

                    } else if (strNewPassword.equals(strConforimPassword)) {
                        registerApiCall();

                    } else et_registration_retypePassword.setError("Password does'nt match ");

                    handled = true;
                }
                return handled;
            }
        });
        return parentView;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void registerApiCall() {

        strFullName = et_registration_fullname.getText().toString();
        strEmail = et_registration_email.getText().toString();
        strPassword = et_registration_password.getText().toString();
        strRetypePassword = et_registration_retypePassword.getText().toString();
        alertDialogSpot.show();

        Call<SignUpResponse> signUpResponseCall = BaseNetworking.apiServices().signUp(strFullName, strEmail, strPassword, strRetypePassword);
        signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    AppRepository.mPutValue(getActivity()).putBoolean("loggedIn", true).commit();
                    AppRepository.mPutValue(getActivity()).putString("mUserId", String.valueOf(response.body().getData().getId())).commit();
                    AppRepository.mPutValue(getActivity()).putString("mUserName", response.body().getData().getFullName()).commit();
                    AppRepository.mPutValue(getActivity()).putString("mUserEmail", response.body().getData().getEmail()).commit();
                    AppRepository.mPutValue(getActivity()).putString("mUserEmail", response.body().getData().getEmail()).commit();
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), DrawerActivity.class));
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                alertDialogSpot.dismiss();
            }
        });
    }


}
