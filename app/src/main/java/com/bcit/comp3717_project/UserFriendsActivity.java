package com.bcit.comp3717_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class UserFriendsActivity extends AppCompatActivity {
    private Button addFriendsBtn, backBtn;
    private String friendName, friendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friends);

        addFriendsBtn = (Button)findViewById(R.id.contacts_button_add_contact);
        addFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserFriendsActivity.this, AddFriendsActivity.class);
                startActivity(intent);
            }
        });

        backBtn = (Button)findViewById(R.id.contacts_button_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent  = new Intent(UserFriendsActivity.this,
                        HomePageActivity.class);
                startActivity(homeIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        friendName = getIntent().getStringExtra("friendName");
        friendEmail = getIntent().getStringExtra("fiendEmail");

        Log.d("myTag", friendName +" UFA");
        Log.d("myTag", friendEmail +" UFA");

    }
}