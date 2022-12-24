package com.example.babybuy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babybuy.R;
import com.example.babybuy.Database.Database;

public class RegisterActivity extends AppCompatActivity {
    EditText Efname, Elname, Eemail, Epassword, Ecpassword;
    Button rbtn;
    TextView redirecttologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Database db = new Database(this);

        //For input value
        Efname = findViewById(R.id.smobile);
        Elname = findViewById(R.id.stitle);
        Eemail = findViewById(R.id.sdescription);
        Epassword = findViewById(R.id.squnatity);
        Ecpassword = findViewById(R.id.sprice);


        //For Registration and Login redirection Button
        rbtn = findViewById(R.id.btnsendsms);
        redirecttologin = findViewById(R.id.rsingup);

        //Clicklistner add for register button
        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //define string for input
                String fname = Efname.getText().toString();
                String lname = Elname.getText().toString();
                String email = Eemail.getText().toString();
                String password = Epassword.getText().toString();
                String cpassword = Ecpassword.getText().toString();


                //Emtpy value checked
                if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter all data", Toast.LENGTH_SHORT).show();
                } else {

                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                        //password length checked
                        if (password.length() >= 8) {

                            //password and confirm password checked
                            if (password.equals(cpassword)) {

                                //email already exist or not checked
                                boolean checkuser = db.checkemail(email);

                                if (checkuser == false) {
                                    Toast.makeText(RegisterActivity.this, "user already exist", Toast.LENGTH_SHORT).show();
                                } else {
                                    //insert data to Database
                                    boolean i = db.insert(fname, lname, email, password);
                                    // after successfully inserted
                                    if (i == true) {
                                        Toast.makeText(RegisterActivity.this, "successfully update", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    }

                                    //incase any problem occured
                                    else {
                                        Toast.makeText(RegisterActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //incase password and confirm password
                            else {
                                Ecpassword.setError("confirm password doesn't match");
                                Toast.makeText(RegisterActivity.this, "confirm password doesn't match", Toast.LENGTH_SHORT).show();
                            }
                        }

                        //if password length doesn't match
                        else {
                            Epassword.setError("minimum 8 length");
                            Toast.makeText(RegisterActivity.this, "Enter 8 digit password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //if user didn't enter valid email address
                    else {
                        Eemail.setError("enter valid email");
                        Toast.makeText(RegisterActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        redirecttologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Redirecting to Login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
