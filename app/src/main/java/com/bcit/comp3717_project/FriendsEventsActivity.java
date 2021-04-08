package com.bcit.comp3717_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FriendsEventsActivity extends AppCompatActivity {

    private Button backBtn;

    DatabaseReference userRef;
    DatabaseReference userContactsRef;

    List<User> allUserList;
    DatabaseReference ref;

    private String userID;

    RecyclerView rvEvents;
    List<Event> eventList;
    List<Contact> contactList;
    private String userEmail;

    DatabaseReference eventRef;

    private String curr_userID;
    DatabaseReference curr_userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_events);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        userContactsRef = userRef.child("Friends");

        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        allUserList = new ArrayList<User>();

        rvEvents = findViewById(R.id.friendEvent_recyclerView);
        eventList = new ArrayList<Event>();

        eventRef = userRef.child("Events");

        contactList = new ArrayList<Contact>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        backBtn = (Button) findViewById(R.id.friendEvent_button_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(FriendsEventsActivity.this,
                        HomePageActivity.class);
                startActivity(homeIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        userContactsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactList.clear();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    Contact contact = dataSnapshot1.getValue(Contact.class);
                    contactList.add(contact);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for(DataSnapshot eventSnapshot: snapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    if (event.isSharedToFriends()){
                        eventList.add(event);
                    }

                }

                for(int i = 0; i < allUserList.size(); i++) {
                    String temp_email = allUserList.get(i).getEmail();
                    if(userEmail.equals(temp_email)){
                        allUserList.get(i).setEventList(eventList);
                    }
                }

//                for (int i = 0; i < contactList.size(); i++) {
//                    String temp_email_contact = contactList.get(i).getEmail();
//                    for (int j = 0; j < allUserList.size(); j++) {
//                        String temp_email_db = allUserList.get(j).getEmail();
//                        if (temp_email_contact.equals(temp_email_db)){
//                            System.out.println("is there? " + allUserList.get(j).getName());
//                            if (allUserList.get(j).getEventList() != null) {
//                                // get all the event list of found email
//                                for (int k = 0; k < allUserList.get(j).getEventList().size(); k++) {
//                                    // check whether it was shared
//                                    // if true
//                                    if (allUserList.get(j).getEventList().get(k).isSharedToFriends()) {
//                                        eventList.add(allUserList.get(j).getEventList().get(k));
//                                    }
//                                }
//                            }
//
//                        }
//
//                    }
//                }
                EventAdapter adapter = new EventAdapter(eventList);
                rvEvents.setAdapter(adapter);
                rvEvents.setLayoutManager(new LinearLayoutManager(FriendsEventsActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FriendsEventsActivity.this,
                        "Something went Wrong.....",
                        Toast.LENGTH_LONG).show();
            }
        });

        // get all users in database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allUserList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user_temp = dataSnapshot.getValue(User.class);
                    allUserList.add(user_temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}