package com.example.attaurrahman.e_complaint.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.configuration.Config;
import com.example.attaurrahman.e_complaint.genralUtils.Utilities;

import java.util.HashMap;
import java.util.Map;


public class SignUpVerifyCodeFragment extends Fragment {

    View parentView;
    String verifycode;
    EditText et_num1,et_num2,et_num3,et_num4,et_num5,et_num6,et_email_verify;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       parentView = inflater.inflate(R.layout.fragment_sign_up_verify_code, container, false);

        Utilities.buttonDeclaration(R.id.btn_verify_submit,parentView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String num1 = Utilities.editTextDeclaration(R.id.et_code_num1,parentView).getText().toString();
                String num2 = Utilities.editTextDeclaration(R.id.et_code_num2,parentView).getText().toString();
                String num3 = Utilities.editTextDeclaration(R.id.et_code_num3,parentView).getText().toString();
                String num4 = Utilities.editTextDeclaration(R.id.et_code_num4,parentView).getText().toString();
                String num5 = Utilities.editTextDeclaration(R.id.et_code_num5,parentView).getText().toString();
                String num6 = Utilities.editTextDeclaration(R.id.et_code_num6,parentView).getText().toString();

                verifycode = num1+num2+num3+num4+num5+num6;

                if (verifycode.length()==6){
                    apiVerifyCode();

                }else {
                    Toast.makeText(getActivity(), "Fill Verify Code", Toast.LENGTH_SHORT).show();
                }


            }
        });

        et_num1 = parentView.findViewById(R.id.et_code_num1);
        et_num2 = parentView.findViewById(R.id.et_code_num2);
        et_num3 = parentView.findViewById(R.id.et_code_num3);
        et_num4 = parentView.findViewById(R.id.et_code_num4);
        et_num5 = parentView.findViewById(R.id.et_code_num5);
        et_num6 = parentView.findViewById(R.id.et_code_num6);





        et_num1.addTextChangedListener(genraltextWatcher);
        et_num2.addTextChangedListener(genraltextWatcher);
        et_num3.addTextChangedListener(genraltextWatcher);
        et_num4.addTextChangedListener(genraltextWatcher);
        et_num5.addTextChangedListener(genraltextWatcher);
        et_num6.addTextChangedListener(genraltextWatcher);




        return parentView;
    }



    private void emailVerifyApiCall() {


        String url = "http://techeasesol.com/postcard/PostCard_apis/forgotpassword";

        Utilities.buttonDeclaration(R.id.btn_forgetPassword_submit,parentView).setEnabled(false);

        final StringRequest postRequest =  new StringRequest(Request.Method.POST, Config.BASE_URL+"verifycode", new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("true")){
                            Toast.makeText(getActivity(), "Succesfull", Toast.LENGTH_SHORT).show();

                            Utilities.linearLayoutDeclaration(R.id.ll_verify_email,parentView).setVisibility(View.GONE);
                            Utilities.linearLayoutDeclaration(R.id.ll_verify_code,parentView).setVisibility(View.VISIBLE);
                            Utilities.buttonDeclaration(R.id.btn_forgetPassword_submit,parentView).setEnabled(true);




                        }else {
                            Toast.makeText(getActivity(), "Wrong Email", Toast.LENGTH_SHORT).show();
                            Utilities.buttonDeclaration(R.id.btn_forgetPassword_submit,parentView).setEnabled(true);
                        }



                        Log.d("zama respose chnage ",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();
                        Utilities.buttonDeclaration(R.id.btn_forgetPassword_submit,parentView).setEnabled(true);
                        Log.d("zama error",error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("email",et_email_verify.getText().toString());


                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(postRequest);

    }

    private void apiVerifyCode() {


        final StringRequest postRequest =  new StringRequest(Request.Method.POST, Config.BASE_URL+"verifycode", new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("true")){

                            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();

                            Utilities.connectFragment(getActivity(),new LoginTabFragment());


                        }else {
                            Toast.makeText(getActivity(), "wrong number", Toast.LENGTH_SHORT).show();
                        }






                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("code", verifycode);


                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(postRequest);





    }



    private TextWatcher genraltextWatcher = new TextWatcher() {
        private View view;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (et_num1.length()==1) {

                et_num2.requestFocus();

            }if (et_num2.length()==1){

                et_num3.requestFocus();

            }if (et_num3.length()==1) {

                et_num4.requestFocus();

            }if (et_num4.length()==1){

                et_num5.requestFocus();

            }if (et_num5.length()==1) {

                et_num6.requestFocus();
            }if (et_num6.length()==1){

            }


        }

        @Override
        public void afterTextChanged(Editable editable) {


        }

    };
}
