package com.bcit.comp3717_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserFriendsActivity extends AppCompatActivity {
    private Button addFriendsBtn, backBtn;
    private String friendName, friendEmail;

    DatabaseReference userRef;
    DatabaseReference currentUser;
    DatabaseReference friendsRef;

    private FirebaseUser user;
    private String userID;


    RecyclerView rvAddedFriend;
    List<User> addedUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friends);


        // routing stuff
        addFriendsBtn = (Button)findViewById(R.id.contacts_button_add_contact);
        addFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserFriendsActivity.this,
                        AddFriendsActivity.class);
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


        // DB initiate
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        currentUser = userRef.child(userID);

        friendsRef = currentUser.child("Friends");


        // recycle view initiate
        rvAddedFriend = findViewById(R.id.contants_recyclerView);
        addedUserList = new ArrayList<User>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        friendName = getIntent().getStringExtra("friendName");
        friendEmail = getIntent().getStringExtra("fiendEmail");

//        Log.d("myTag", friendName +" UFA");
//        Log.d("myTag", friendEmail +" UFA");

        // add friend to user db
        String id = friendsRef.push().getKey();
        User user = new User(friendName, friendEmail);

        Task<Void> setValueTask = friendsRef.child(id).setValue(user);


        // read from db: all current user's added friend
        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addedUserList.clear();
                for (DataSnapshot addedFriendSnapshot: snapshot.getChildren()){
                    User myAddedFriend = addedFriendSnapshot.getValue(User.class);
                    addedUserList.add(myAddedFriend);
                }

                // connect to rv
                AddFriendAdapter adapter = new AddFriendAdapter(addedUserList);
                rvAddedFriend.setAdapter(adapter);
                rvAddedFriend.setLayoutManager(new LinearLayoutManager(
                        UserFriendsActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserFriendsActivity.this,
                        "Something went Wrong.....",
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}