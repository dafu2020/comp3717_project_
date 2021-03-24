package com.bcit.comp3717_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    private TextView register, resetPassword;
    private EditText editTextEmail, editTextPassword;
    private Button loginBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.main_editText_email);
        editTextPassword = (EditText) findViewById(R.id.main_editText_Password);


        register = (TextView) findViewById(R.id.main_tv_register);
        register.setOnClickListener(registerOnClick);

        resetPassword = (TextView) findViewById(R.id.main_forget_pw);
        resetPassword.setOnClickListener(resetPasswordOnClick);

        loginBtn = (Button) findViewById(R.id.main_btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private final View.OnClickListener registerOnClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent registerIntent  = new Intent(MainActivity.this,
                    RegisterUser.class);
            startActivity(registerIntent);
        }
    };

    private final View.OnClickListener resetPasswordOnClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent resetIntent = new Intent(MainActivity.this,
                    ResetPasswordActivity.class);
            startActivity(resetIntent);
        }
    };

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent homeIntent  = new Intent(MainActivity.this,
                            HomePageActivity.class);
                    startActivity(homeIntent);

                }else {
                    Toast.makeText(MainActivity.this,
                            "Failed to login! Please CHECK YOUR CREDENTIAL ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}