package com.bcit.comp3717_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ModifyEventActivity extends AppCompatActivity {

    private Button backBtn, editBtn, deleteBtn;
    private EditText etEventName, etEventLocation, etEventDescription, etEventDate, etEventTime;
    private CheckBox sharedToFriend;

    DatabaseReference userRef;
    DatabaseReference currentUser;
    DatabaseReference eventRef;

    List<Event> eventList;

    private FirebaseUser user;
    private String userID, eventId, eventName, _LOC, _DES, _DATE, _TIME;
    private boolean isShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_event);

        backBtn = (Button)findViewById(R.id.modifyEvent_button_back);
        editBtn = (Button)findViewById(R.id.modifyEvent_button_editEvent);
        deleteBtn = (Button) findViewById(R.id.modifyEvent_button_delete);

        etEventName = (EditText) findViewById(R.id.modifyEvent_editText_name);
        etEventLocation = (EditText) findViewById(R.id.modifyEvent_editText_location);
        etEventDescription = (EditText) findViewById(R.id.modifyEvent_editText_description);
        etEventDate = (EditText) findViewById(R.id.modifyEvent_editText_date);
        etEventTime = (EditText) findViewById(R.id.modifyEvent_editText_time);

        sharedToFriend = (CheckBox) findViewById(R.id.modifyEvent_checkbox_share);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // get values from the event item
        eventName = getIntent().getStringExtra("eventName");
        _LOC  = getIntent().getStringExtra("eventLoc");
        _DES = getIntent().getStringExtra("eventDes");
        _DATE = getIntent().getStringExtra("eventDate");
        _TIME = getIntent().getStringExtra("eventTime");
        isShared = getIntent().getBooleanExtra("shareToFriend",
                false);

        etEventName.setText(eventName);
        etEventLocation.setText(_LOC);
        etEventDescription.setText(_DES);
        etEventDate.setText(_DATE);
        etEventTime.setText(_TIME);
        sharedToFriend.setChecked(isShared);

        eventId = getIntent().getStringExtra("eventId");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        currentUser = userRef.child(userID);
        eventRef = currentUser.child("Events").child(eventId);


        // update the event
        // todo debug - cannot update multiple time
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eventName.equals(etEventName.getText().toString().trim())) {
                    eventRef.child("eventName").setValue(etEventName.getText().toString().trim());
                }

                if (!_LOC.equals(etEventLocation.getText().toString().trim())) {
                    eventRef.child("eventLocation").setValue(etEventLocation.getText().toString().trim());
                }

                if (!_DES.equals(etEventDescription.getText().toString().trim())) {
                    eventRef.child("eventDescription").setValue(etEventDescription.getText().toString().trim());
                }

                if (!_DATE.equals(etEventDate.getText().toString().trim())) {
                    eventRef.child("eventDate").setValue(etEventDate.getText().toString().trim());
                }

                if (!_TIME.equals(etEventTime.getText().toString().trim())) {
                    eventRef.child("eventTime").setValue(etEventTime.getText().toString().trim());
                }

                if (isShared !=(sharedToFriend.isChecked())) {
                    eventRef.child("sharedToFriends").setValue(sharedToFriend.isChecked());
                }



            }
        });

        // delete
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventRef = currentUser.child("Events").child(eventId);
                eventRef.removeValue();
                Intent addEventIntent  = new Intent(ModifyEventActivity.this,
                        AddEventsActivity.class);
                startActivity(addEventIntent);
            }
        });

        // go back to addEvent page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEventIntent  = new Intent(ModifyEventActivity.this,
                        AddEventsActivity.class);
                startActivity(addEventIntent);
            }
        });


    }
}