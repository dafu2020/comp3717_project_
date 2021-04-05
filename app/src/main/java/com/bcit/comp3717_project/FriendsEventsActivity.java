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

    RecyclerView rvEvents;
    EventAdapter eventAdapter;
    Calendar myCalendar = Calendar.getInstance();

    DatabaseReference currentUser;
    DatabaseReference userContactsRef;
    DatabaseReference friendsRef;

    private List<Event> eventList;
    private List<Event> contactList;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_events);

        rvEvents = findViewById(R.id.friendEvent_recyclerView);
        eventList = new ArrayList<Event>();

        //database connections and current user info
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        currentUser = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        userContactsRef = currentUser.child("Contacts");



        backBtn = (Button)findViewById(R.id.friendEvent_button_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent  = new Intent(FriendsEventsActivity.this,
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
                for(DataSnapshot eventSnapshot: snapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    contactList.add(event);
                }
                EventAdapter adapter = new EventAdapter(contactList);
                rvEvents.setAdapter(adapter);
                rvEvents.setLayoutManager(new LinearLayoutManager(FriendsEventsActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FriendsEventsActivity.this,
                        "Unknown Error occurred, please try again.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}