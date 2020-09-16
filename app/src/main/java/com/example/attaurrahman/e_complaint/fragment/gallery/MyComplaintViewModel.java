package com.example.attaurrahman.e_complaint.fragment.gallery;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MyComplaintViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyComplaintViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}