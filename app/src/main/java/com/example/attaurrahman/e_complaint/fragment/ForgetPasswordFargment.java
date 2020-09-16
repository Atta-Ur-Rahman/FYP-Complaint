package com.example.attaurrahman.e_complaint.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.configuration.CheckNetwork;
import com.example.attaurrahman.e_complaint.dataModel.ForgotPasswordResponse;
import com.example.attaurrahman.e_complaint.dataModel.ForgotVerifyResponse;
import com.example.attaurrahman.e_complaint.genralUtils.AppRepository;
import com.example.attaurrahman.e_complaint.genralUtils.Utilities;
import com.example.attaurrahman.e_complaint.networking.BaseNetworking;
import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class ForgetPasswordFargment extends Fragment {

    View parentView;
    String verifycode;
    EditText et_num1, et_num2, et_num3, et_num4, et_email_verify;

    String  strEmail;
    AlertDialog alertDialogSpot;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_forget_password_fargment, container, false);
        alertDialogSpot = new SpotsDialog(getActivity(), R.style.CustomLoading);

        et_email_verify = Utilities.editTextDeclaration(R.id.et_email_verify, parentView);


        Utilities.buttonDeclaration(R.id.btn_forgetPassword_submit, parentView).setOnClickListener(new View.OnClickListener() {
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

                    if (et_email_verify.getText().length() <= 1) {

                        et_email_verify.setError("Enter Email");

                    } else if (!Utilities.isValidEmail(et_email_verify.getText())) {

                        et_email_verify.setError("Correct Format Email");

                    } else
                        emailVerifyApiCall();
                }
            }
        });

        Utilities.buttonDeclaration(R.id.btn_forget_verify_submit, parentView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String num1 = Utilities.editTextDeclaration(R.id.et_code_num1, parentView).getText().toString();
                String num2 = Utilities.editTextDeclaration(R.id.et_code_num2, parentView).getText().toString();
                String num3 = Utilities.editTextDeclaration(R.id.et_code_num3, parentView).getText().toString();
                String num4 = Utilities.editTextDeclaration(R.id.et_code_num4, parentView).getText().toString();


                verifycode = num1 + num2 + num3 + num4;

                if (verifycode.length() == 4) {
                    apiVerifyCode();

                } else {
                    Toast.makeText(getActivity(), "Fill Verify Code", Toast.LENGTH_SHORT).show();
                }


            }
        });

        et_num1 = parentView.findViewById(R.id.et_code_num1);
        et_num2 = parentView.findViewById(R.id.et_code_num2);
        et_num3 = parentView.findViewById(R.id.et_code_num3);
        et_num4 = parentView.findViewById(R.id.et_code_num4);


        et_num1.addTextChangedListener(genraltextWatcher);
        et_num2.addTextChangedListener(genraltextWatcher);
        et_num3.addTextChangedListener(genraltextWatcher);
        et_num4.addTextChangedListener(genraltextWatcher);


        return parentView;
    }


    private void emailVerifyApiCall() {
        alertDialogSpot.show();
        strEmail = et_email_verify.getText().toString();

        Call<ForgotPasswordResponse> forgotPasswordResponseCall = BaseNetworking.apiServices().forgotPassword(strEmail);
        forgotPasswordResponseCall.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, retrofit2.Response<ForgotPasswordResponse> response) {


                if (response.isSuccessful()) {

                    alertDialogSpot.dismiss();
                    Utilities.linearLayoutDeclaration(R.id.ll_verify_email, parentView).setVisibility(View.GONE);
                    Utilities.linearLayoutDeclaration(R.id.ll_verify_code, parentView).setVisibility(View.VISIBLE);
                    Utilities.buttonDeclaration(R.id.btn_forgetPassword_submit, parentView).setEnabled(true);


                } else {
                    alertDialogSpot.dismiss();
                    Toast.makeText(getActivity(), "Wrong Email", Toast.LENGTH_SHORT).show();
                    Utilities.buttonDeclaration(R.id.btn_forgetPassword_submit, parentView).setEnabled(true);

                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Utilities.buttonDeclaration(R.id.btn_forgetPassword_submit, parentView).setEnabled(true);
                alertDialogSpot.dismiss();
            }
        });

    }

    private void apiVerifyCode() {

        alertDialogSpot.show();

        Call<ForgotVerifyResponse> forgotVerifyResponseCall = BaseNetworking.apiServices().forgotVerify(strEmail, verifycode);
        forgotVerifyResponseCall.enqueue(new Callback<ForgotVerifyResponse>() {
            @Override
            public void onResponse(Call<ForgotVerifyResponse> call, retrofit2.Response<ForgotVerifyResponse> response) {
                if (response.isSuccessful()) {
                    AppRepository.mPutValue(getActivity()).putString("verify_email", et_email_verify.getText().toString()).commit();
                    Utilities.connectFragmentWithOutBackStack(getActivity(), new ChangePasswordFragment());

                    alertDialogSpot.dismiss();
                } else {
                    Toast.makeText(getActivity(), "wrong number", Toast.LENGTH_SHORT).show();

                    alertDialogSpot.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ForgotVerifyResponse> call, Throwable t) {
                alertDialogSpot.dismiss();
            }
        });
    }


    private TextWatcher genraltextWatcher = new TextWatcher() {
        private View view;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (et_num1.length() == 1) {

                et_num2.requestFocus();

            }
            if (et_num2.length() == 1) {

                et_num3.requestFocus();

            }
            if (et_num3.length() == 1) {

                et_num4.requestFocus();

            }
            if (et_num4.length() == 1) {


            }


        }

        @Override
        public void afterTextChanged(Editable editable) {


        }

    };


}
