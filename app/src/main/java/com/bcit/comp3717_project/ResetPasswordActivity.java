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

public class ResetPasswordActivity extends AppCompatActivity {

    private Button resetPasswordBtn;
    private TextView banner;
    private EditText editTextEmail;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextEmail = (EditText) findViewById(R.id.reset_textEditor_email);
        auth = FirebaseAuth.getInstance();


        banner = (TextView) findViewById(R.id.reset_tv_logo);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent  = new Intent(ResetPasswordActivity.this,
                        MainActivity.class);
                startActivity(mainIntent);
            }
        });

        resetPasswordBtn = (Button) findViewById(R.id.reset_button_resetPasswrod);
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email = editTextEmail.getText().toString().trim();

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

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this,
                            "Please check your email to reset your password.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ResetPasswordActivity.this,
                            "Something went wrong.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}