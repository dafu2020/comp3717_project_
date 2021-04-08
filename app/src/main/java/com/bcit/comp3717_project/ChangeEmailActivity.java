package com.bcit.comp3717_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeEmailActivity extends AppCompatActivity {


    private TextView userName;
    private Button update;
    private EditText editTextEmail,  editTextPassword,
            editTextNewEmail, editTextNewEmailConfirm;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        //userName = (TextView) findViewById(R.id.home_tv_username);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        update = (Button) findViewById(R.id.auth_btn_update_email);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reauthenticate();
            }
        });

        editTextEmail = (EditText) findViewById(R.id.auth_te_email_old);
        editTextPassword = (EditText) findViewById(R.id.auth_te_password_email);
        editTextNewEmail = (EditText) findViewById(R.id.auth_te_newEmail);
        editTextNewEmailConfirm = (EditText) findViewById(R.id.auth_te_newEmail_confirm);

    }

    public void reauthenticate() {
        // [START reauthenticate]
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String newEmail = editTextNewEmail.getText().toString().trim();
        String newEmailConfirm = editTextNewEmailConfirm.getText().toString().trim();


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

        if(newEmail.isEmpty()) {
            editTextNewEmail.setError("New Email is required");
            editTextNewEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            editTextNewEmail.setError("Please provide valid email!");
            editTextNewEmail.requestFocus();
            return;
        }

        if(newEmailConfirm.isEmpty()) {
            editTextNewEmailConfirm.setError("Please confirm new email");
            editTextNewEmailConfirm.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(newEmailConfirm).matches()) {
            editTextNewEmailConfirm.setError("Please provide valid email!");
            editTextNewEmailConfirm.requestFocus();
            return;
        }

        if(!newEmail.equals(newEmailConfirm)){
            editTextNewEmail.setError("New Email must match confirmation");
            editTextNewEmail.requestFocus();
            return;
        }

        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ChangeEmailActivity.this,
                                "User Authenticated",
                                Toast.LENGTH_SHORT).show();
                        updateEmail(newEmail);
                    }
                });
        // [END reauthenticate]
    }

    public void updateEmail(String newEmail) {
        // [START update_email]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(newEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ChangeEmailActivity.this,
                                    "Email Updated",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        // [END update_email]
    }

}