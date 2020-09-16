
package com.example.attaurrahman.e_complaint.dataModel.signUpModel;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private SignUpDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public SignUpDataModel getData() {
        return mData;
    }

    public void setData(SignUpDataModel data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
