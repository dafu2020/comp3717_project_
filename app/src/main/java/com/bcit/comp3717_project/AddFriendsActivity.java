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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsActivity extends AppCompatActivity {
    private Button backBtn;

    DatabaseReference userRef;
    DatabaseReference currentUser;


    RecyclerView rvFriends;
    List<Friends> friendsList;

    private FirebaseUser user;
    private String userID;
    String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_freinds);

        backBtn = (Button)findViewById(R.id.addFriend_button_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent  = new Intent(AddFriendsActivity.this,
                        UserFriendsActivity.class);
                startActivity(homeIntent);
            }
        });

        userRef = FirebaseDatabase.getInstance().getReference("Users");
        rvFriends = findViewById(R.id.addFriend_recyclerView);
        friendsList = new ArrayList<Friends>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        currentUser = userRef.child(userID);



    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userEmail = snapshot.child("email").getValue(String.class);
                Log.d("myTag", userEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddFriendsActivity.this,
                        "Something went Wrong.....",
                        Toast.LENGTH_LONG).show();
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friendsList.clear();
                for(DataSnapshot eventSnapshot: snapshot.getChildren()) {
                    Friends friends = eventSnapshot.getValue(Friends.class);
                    if(! friends.getEmail().equals(userEmail)){
                        friendsList.add(friends);
                    }
                }

                FriendsAdapter adapter = new FriendsAdapter(friendsList);
                rvFriends.setAdapter(adapter);
                rvFriends.setLayoutManager(new LinearLayoutManager(AddFriendsActivity.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddFriendsActivity.this,
                        "Something went Wrong.....",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}