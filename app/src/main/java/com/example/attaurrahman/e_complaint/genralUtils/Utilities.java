package com.example.attaurrahman.e_complaint.genralUtils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.fragment.ChangePasswordFragment;

import java.util.regex.Pattern;

/**
 * Created by kashif on 1/10/18.
 */

public class Utilities {
    public static Button button;
    public static TextView textView;
    public static EditText editText;
    public static LinearLayout linearLayout;
    public static TextWatcher textWatcher;
    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");




    public static Fragment connectFragment(Context context, Fragment fragment) {
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("true").commit();
        return fragment;
    }

    public static Fragment connectFragmentWithOutBackStack(Context context, Fragment fragment) {
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        return fragment;
    }
    public static Fragment connectFragmentForMyComplaint(Context context, Fragment fragment) {
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        return fragment;
    }

    public static Button buttonDeclaration(int buttonID, View view) {
        button = view.findViewById(buttonID);
        return button;
    }

    public static TextView textViewDeclaration(int textViewID, View view) {
        textView = view.findViewById(textViewID);

        //    movieList = new ArrayList<>();
//    postCardListAdapter = new BooksAdapter(movieList);
//    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(postCardListAdapter);
//    prepareMovieData();

        return textView;
    }
    public static EditText editTextDeclaration(int edtihTextID, View view){

        editText = view.findViewById(edtihTextID);

        return editText;
    }

    public static LinearLayout linearLayoutDeclaration(int linearLayoutID,View view ){
        linearLayout = view.findViewById(linearLayoutID);
        return linearLayout;
    }

    public static TextWatcher textWatcherDeclaration(int textWatcherID, View view){
        textWatcher = view.findViewById(textWatcherID);
        return textWatcher;
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }




}
