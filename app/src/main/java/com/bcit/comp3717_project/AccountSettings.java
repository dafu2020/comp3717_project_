package com.bcit.comp3717_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountSettings extends AppCompatActivity {

    private TextView userName;
    private Button logOut, changeEmail, changePwd;

    //jennie
    private Button accountSettings;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);


        userName = (TextView) findViewById(R.id.home_tv_username);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUserProfile = dataSnapshot.getValue(User.class);

                if(currentUserProfile !=null) {
                    String currentUserName = currentUserProfile.name;
                    userName.setText(currentUserName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountSettings.this,
                        "Something went Wrong.....",
                        Toast.LENGTH_LONG).show();
            }

        });

        logOut = (Button) findViewById(R.id.home_button_logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent mainIntent  = new Intent(AccountSettings.this,
                        MainActivity.class);
                startActivity(mainIntent);
            }
        });


        changePwd = (Button)findViewById(R.id.account_settings_pwd);
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent  = new Intent(AccountSettings.this,
                        ChangePwdActivity.class);
                startActivity(settingsIntent);
            }
        });

        changeEmail = (Button)findViewById(R.id.account_settings_email);
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent  = new Intent(AccountSettings.this,
                        ChangeEmailActivity.class);
                startActivity(settingsIntent);
            }
        });

    }


}