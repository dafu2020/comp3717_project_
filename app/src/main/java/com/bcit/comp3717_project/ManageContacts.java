package com.bcit.comp3717_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ManageContacts extends AppCompatActivity {

    private Button logOut;
    private Button addContact;
    private EditText contactName;
    private EditText contactEmail;

    RecyclerView rvContacts;
    List<Contact> contactList;

    DatabaseReference currentUser;
    DatabaseReference userContactsRef;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_contacts);

        //database connections and current user info
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        currentUser = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        userContactsRef = currentUser.child("Contacts");

        //set ui variables
        rvContacts = findViewById(R.id.manage_contacts_recyclerView);
        contactList = new ArrayList<Contact>();
        contactEmail = findViewById(R.id.contact_email);
        contactName = findViewById(R.id.contact_name);
        logOut = (Button) findViewById(R.id.home_button_logout);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent mainIntent  = new Intent(ManageContacts.this,
                        MainActivity.class);
                startActivity(mainIntent);
            }
        });

        addContact = (Button) findViewById(R.id.contact_add);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
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
                    Contact contact = eventSnapshot.getValue(Contact.class);
                    contactList.add(contact);
                }
                ContactAdapter adapter = new ContactAdapter(contactList);
                rvContacts.setAdapter(adapter);
                rvContacts.setLayoutManager(new LinearLayoutManager(ManageContacts.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManageContacts.this,
                        "Unknown Error occurred, please try again.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addContact(){
        String name = contactName.getText().toString().trim();
        String email = contactEmail.getText().toString().trim();

        if(name.isEmpty()) {
            contactName.setError("Contact Name missing.");
            contactName.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            contactEmail.setError("Contact Email missing.");
            contactEmail.requestFocus();
            return;
        }


        String contactsId = userContactsRef.push().getKey();
        Contact contact = new Contact(name, email);

        Task<Void> addContactTask = userContactsRef.child(contactsId).setValue(contact);

        addContactTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(ManageContacts.this, "Contact added",
                        Toast.LENGTH_LONG).show();
                contactName.setText("");
                contactEmail.setText("");
            }
        });

        addContactTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ManageContacts.this,
                        "Unknown Error occurred, please try again.",
                        Toast.LENGTH_LONG).show();
            }
        });


    }
}