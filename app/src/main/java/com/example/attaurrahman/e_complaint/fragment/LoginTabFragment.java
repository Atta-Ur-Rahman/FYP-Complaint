package com.example.attaurrahman.e_complaint.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.activity.DrawerActivity;
import com.example.attaurrahman.e_complaint.configuration.CheckNetwork;
import com.example.attaurrahman.e_complaint.dataModel.loginModel.LogInResponse;
import com.example.attaurrahman.e_complaint.genralUtils.AppRepository;
import com.example.attaurrahman.e_complaint.genralUtils.Utilities;
import com.example.attaurrahman.e_complaint.networking.BaseNetworking;
import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class LoginTabFragment extends Fragment implements View.OnClickListener {

    View parentView;
    EditText et_complaint_email, et_complaint_password;
    AlertDialog alertDialogSpot;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_login_tab, container, false);


        alertDialogSpot = new SpotsDialog(getActivity(), R.style.CustomLoading);

        // Utilities.textViewDeclaration(R.id.tv_login, parentView).setTextAppearance(R.style.boldText);

        et_complaint_email = Utilities.editTextDeclaration(R.id.et_complaint_email, parentView);
        et_complaint_password = Utilities.editTextDeclaration(R.id.et_complaint_password, parentView);

        Utilities.textViewDeclaration(R.id.tv_register, parentView).setOnClickListener(this);
        Utilities.textViewDeclaration(R.id.tv_forgetPassword, parentView).setOnClickListener(this);
        Utilities.buttonDeclaration(R.id.btn_login, parentView).setOnClickListener(this);


        et_complaint_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_complaint_email.setFocusableInTouchMode(true);
                et_complaint_password.setFocusableInTouchMode(true);
                return false;
            }
        });


        return parentView;
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.btn_login:
                if (!CheckNetwork.isInternetAvailable(getActivity())) {
                    noInternet();
                } else {
                    if (et_complaint_email.getText().length() <= 1) {
                        Toast.makeText(getActivity(), "Enter Your Email", Toast.LENGTH_SHORT).show();

                    } else if (!Utilities.isValidEmail(et_complaint_email.getText())) {
                        Toast.makeText(getActivity(), "Correct Format Email", Toast.LENGTH_SHORT).show();
                    } else
                        loginApiCall();
                }

                break;

            case R.id.tv_register:

                Utilities.connectFragment(getActivity(), new RegistrationTabFragment());
                break;

            case R.id.tv_forgetPassword:
                Utilities.connectFragment(getActivity(), new ForgetPasswordFargment());
                break;
        }
    }

    private void noInternet() {
        new CDialog(getActivity()).createAlert("No Internet Connection",
                CDConstants.WARNING,   // Type of dialog
                CDConstants.LARGE)    //  size of dialog
                .setAnimation(CDConstants.SCALE_TO_TOP_FROM_BOTTOM)     //  Animation for enter/exit
                .setDuration(5000)   // in milliseconds
                .setTextSize(CDConstants.LARGE_TEXT_SIZE)  // CDConstants.LARGE_TEXT_SIZE, CDConstants.NORMAL_TEXT_SIZE
                .show();

    }


    private void loginApiCall() {
        alertDialogSpot.show();
        Call<LogInResponse> logInResponseCall = BaseNetworking.apiServices().logIn(et_complaint_email.getText().toString(), et_complaint_password.getText().toString());
        logInResponseCall.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, retrofit2.Response<LogInResponse> response) {
                if (response.isSuccessful()) {
                    alertDialogSpot.dismiss();
                    AppRepository.mPutValue(getActivity()).putBoolean("loggedIn", true).commit();
                    AppRepository.mPutValue(getActivity()).putString("mUserId", String.valueOf(response.body().getData().getId())).commit();
                    AppRepository.mPutValue(getActivity()).putString("mUserName", response.body().getData().getFullName()).commit();
                    AppRepository.mPutValue(getActivity()).putString("mUserEmail", response.body().getData().getEmail()).commit();
                    AppRepository.mPutValue(getActivity()).putString("mUserEmail", response.body().getData().getEmail()).commit();
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), DrawerActivity.class));

                } else {
                    alertDialogSpot.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                alertDialogSpot.dismiss();
            }
        });
    }

}



