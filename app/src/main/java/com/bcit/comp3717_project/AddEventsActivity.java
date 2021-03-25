package com.bcit.comp3717_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class AddEventsActivity extends AppCompatActivity {
    private Button backBtn, addEventBtn;

    private EditText etEventName, etEventLocation, etEventDescription, etEventDate, etEventTime;
    private CheckBox sharedToFriend;

    RecyclerView rvEvents;
    List<Event> eventList;

    DatabaseReference userRef;
    DatabaseReference currentUser;
    DatabaseReference eventRef;

    private FirebaseUser user;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);


        userRef = FirebaseDatabase.getInstance().getReference("Users");

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        currentUser = userRef.child(userID);

        eventRef = currentUser.child("Events");

        backBtn = (Button)findViewById(R.id.addEvent_button_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent  = new Intent(AddEventsActivity.this,
                        HomePageActivity.class);
                startActivity(homeIntent);
            }
        });

        rvEvents = findViewById(R.id.addEvent_recyclerView);
        eventList = new ArrayList<Event>();

        etEventName = (EditText) findViewById(R.id.addEvent_editText_name);
        etEventLocation = (EditText) findViewById(R.id.addEvent_editText_location);
        etEventDescription = (EditText) findViewById(R.id.addEvent_editText_description);
        etEventDate = (EditText) findViewById(R.id.addEvent_editText_date);
        etEventTime = (EditText) findViewById(R.id.addEvent_editText_time);

        sharedToFriend = (CheckBox) findViewById(R.id.addEvent_checkbox_share);

        addEventBtn = (Button) findViewById(R.id.addEvent_button_addEvent);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for(DataSnapshot eventSnapshot: snapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    eventList.add(event);
                }
                EventAdapter adapter = new EventAdapter(eventList);
                rvEvents.setAdapter(adapter);
                rvEvents.setLayoutManager(new LinearLayoutManager(AddEventsActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddEventsActivity.this,
                        "Something went Wrong.....",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addEvent(){
        String  eventName = etEventName.getText().toString().trim();
        String eventLoc = etEventLocation.getText().toString().trim();
        String eventDes = etEventDescription.getText().toString().trim();
        String eventDate = etEventDate.getText().toString().trim();
        String eventTime = etEventTime.getText().toString().trim();
        boolean isChecked = sharedToFriend.isChecked();


        if(eventName.isEmpty()) {
            etEventName.setError("Event name is required.");
            etEventName.requestFocus();
            return;
        }

        if(eventLoc.isEmpty()) {
            etEventLocation.setError("Event location is required.");
            etEventLocation.requestFocus();
            return;
        }

        if(eventDes.isEmpty()) {
            etEventDescription.setError("Event description is required.");
            etEventDescription.requestFocus();
            return;
        }

        if(eventDate.isEmpty()) {
            etEventDate.setError("Event date is required.");
            etEventDate.requestFocus();
            return;
        }

        if(eventTime.isEmpty()) {
            etEventTime.setError("Event time is required.");
            etEventTime.requestFocus();
            return;
        }

        String id = eventRef.push().getKey();
        Event event = new Event(eventName, eventLoc, eventDes, eventDate, eventTime, isChecked);

        Task<Void> setValueTask = eventRef.child(id).setValue(event);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(AddEventsActivity.this, "New event added",
                        Toast.LENGTH_LONG).show();

                etEventName.setText("");
                etEventLocation.setText("");
                etEventDescription.setText("");
                etEventDate.setText("");
                etEventTime.setText("");
                etEventDescription.setText("");
                sharedToFriend.setChecked(false);
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddEventsActivity.this,
                        "Something Went Wrong",
                        Toast.LENGTH_LONG).show();
            }
        });


    }


}