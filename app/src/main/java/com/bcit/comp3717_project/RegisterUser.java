package com.bcit.comp3717_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText  editTextName,  editTextEmail,  editTextPassword;
    private Button registerUser;
    private TextView banner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.register_tv_logo);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent  = new Intent(RegisterUser.this,
                        MainActivity.class);
                startActivity(mainIntent);
            }
        });

        Button registerUser = findViewById(R.id.register_btn_register);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();

            }
        });

        editTextName = (EditText) findViewById(R.id.register_te_name);
        editTextEmail = (EditText) findViewById(R.id.register_te_email);
        editTextPassword = (EditText) findViewById(R.id.register_te_password);

    }

    private void registerNewUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();

        if(name.isEmpty()) {
            editTextName.setError("User name is required for registration!");
            editTextName.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required for registration!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password is required for registration!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editTextPassword.setError("Password need to be more than 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

    }
}