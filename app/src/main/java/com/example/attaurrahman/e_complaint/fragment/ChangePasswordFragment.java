package com.example.attaurrahman.e_complaint.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.activity.LoginSignUpActivity;
import com.example.attaurrahman.e_complaint.dataModel.ChangePasswordResponse;
import com.example.attaurrahman.e_complaint.genralUtils.AppRepository;
import com.example.attaurrahman.e_complaint.genralUtils.Utilities;
import com.example.attaurrahman.e_complaint.networking.BaseNetworking;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class ChangePasswordFragment extends Fragment {

    View parentView;
    EditText et_forgetPassword_retypePassword, et_newPassword;

    AlertDialog alertDialogSpot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_change_password, container, false);
        alertDialogSpot = new SpotsDialog(getActivity(), R.style.CustomLoading);


        et_newPassword = Utilities.editTextDeclaration(R.id.et_forgetPassword_retypePassword, parentView);
        et_forgetPassword_retypePassword = Utilities.editTextDeclaration(R.id.et_forgetPassword_retypePassword, parentView);

        Utilities.buttonDeclaration(R.id.btn_change_password_submit, parentView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strNewPassword = et_newPassword.getText().toString();
                String strConfirmPassword = et_forgetPassword_retypePassword.getText().toString();

                if (et_newPassword.getText().equals("") || et_newPassword.getText().length() < 5) {
                    et_newPassword.setError("Please more then 6 digit password");

                } else if (strNewPassword.equals("") || !strNewPassword.equals(strConfirmPassword)) {

                    et_newPassword.setError("Password Not Match");

                } else changePasswordApi();


            }
        });


        return parentView;
    }

    private void changePasswordApi() {
        alertDialogSpot.show();
        Call<ChangePasswordResponse> changePasswordResponseCall = BaseNetworking.apiServices().changePassword(AppRepository.mUserVerifyEmail(getActivity()), et_forgetPassword_retypePassword.getText().toString());
        changePasswordResponseCall.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, retrofit2.Response<ChangePasswordResponse> response) {
                if (response.isSuccessful()) {
                    getActivity().finish();
                    AppRepository.mPutValue(getActivity()).putString("verify_email", "").commit();
                    startActivity(new Intent(getActivity(), LoginSignUpActivity.class));
                    alertDialogSpot.dismiss();
                } else {
                    alertDialogSpot.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                alertDialogSpot.dismiss();
            }
        });
    }

}
