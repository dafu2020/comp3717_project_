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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePwdActivity extends AppCompatActivity {


    private TextView userName;
    private Button update;
    private EditText editTextEmail,  editTextPassword,
            editTextNewPassword, editTextNewPasswordConfirm;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        //userName = (TextView) findViewById(R.id.home_tv_username);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        update = (Button) findViewById(R.id.auth_btn_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reauthenticate();
            }
        });

        editTextEmail = (EditText) findViewById(R.id.auth_te_email);
        editTextPassword = (EditText) findViewById(R.id.auth_te_password);
        editTextNewPassword = (EditText) findViewById(R.id.auth_te_newPass);
        editTextNewPasswordConfirm = (EditText) findViewById(R.id.auth_te_newPass_confirm);

    }

    public void reauthenticate() {
        // [START reauthenticate]
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String newPasswordConfirm = editTextNewPasswordConfirm.getText().toString().trim();


        if(email.isEmpty()) {
            editTextEmail.setError("Current Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Current Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(newPassword.isEmpty()) {
            editTextNewPassword.setError("New Password is required");
            editTextNewPassword.requestFocus();
            return;
        }

        if(newPassword.length() < 6) {
            editTextNewPassword.setError("New Password needs to be more than 6 characters");
            editTextNewPassword.requestFocus();
            return;
        }

        if(newPasswordConfirm.isEmpty()) {
            editTextNewPasswordConfirm.setError("Please confirm new password");
            editTextNewPasswordConfirm.requestFocus();
            return;
        }

        if(!newPassword.equals(newPasswordConfirm)){
            editTextNewPassword.setError("New Password must match confirmation");
            editTextNewPassword.requestFocus();
            return;
        }

        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ChangePwdActivity.this,
                                "User Authenticated",
                                Toast.LENGTH_SHORT).show();
                        updatePassword(newPassword);
                    }
                });
        // [END reauthenticate]
    }

    public void updatePassword(String newPassword) {
        // [START update_password]

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ChangePwdActivity.this,
                                    "Password Updated",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        // [END update_password]
    }
}