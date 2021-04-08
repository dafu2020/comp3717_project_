package com.bcit.comp3717_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contacts) {
        this.contactList = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        ContactViewHolder viewHolder = new ContactViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        TextView textViewNickname = holder.nickname;
        TextView textViewEmail = holder.email;
        Contact contact = contactList.get(position);

        final String contactNickname = contact.getNickname();
        final String contactEmail = contact.getEmail();

        textViewNickname.setText(contactNickname);
        textViewEmail.setText(contactEmail);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}
