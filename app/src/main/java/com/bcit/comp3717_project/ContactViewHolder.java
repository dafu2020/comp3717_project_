package com.bcit.comp3717_project;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactViewHolder extends RecyclerView.ViewHolder {
    public TextView nickname, email;

    public ContactViewHolder(@NonNull View itemView) {
        super(itemView);

        nickname = itemView.findViewById(R.id.textView_Contact_nickname);
        email = itemView.findViewById(R.id.textView_Contact_email);
    }
}
