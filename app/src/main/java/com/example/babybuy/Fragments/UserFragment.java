package com.example.babybuy.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.babybuy.Activity.LoginActivity;
import com.example.babybuy.Database.Database;
import com.example.babybuy.Model.AuthDatamodel;
import com.example.babybuy.R;

import java.util.ArrayList;


public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }


    TextView userfragFullname, userfragEmail,userfragFullnameforlogo;
    String emailid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user, container, false);
        Database db = new Database(getActivity());
        userfragFullname = view.findViewById(R.id.userfragfullname);
        userfragEmail = view.findViewById(R.id.userfragEmail);
        userfragFullnameforlogo = view.findViewById(R.id.tv_name);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Login", 0);
        String value = sharedPreferences.getString("email", "null");

        if(value != null){
            String fullname = db.getfullname(value);
            userfragEmail.setText(value);
            userfragFullname.setText(fullname);
            userfragFullnameforlogo.setText(fullname);
        }
        return view;
    }
}