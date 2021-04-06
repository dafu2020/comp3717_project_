package com.bcit.comp3717_project;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTV;
    public TextView emailTV;
    public TextView idTV;

    public Button addBtn;


    public FriendsViewHolder(@NonNull View itemView) {
        super(itemView);

        nameTV = itemView.findViewById(R.id.addFriends_textView_name);
        emailTV = itemView.findViewById(R.id.addFriends_textView_email);
        idTV = itemView.findViewById(R.id.addFriends_textView_id);

        addBtn = itemView.findViewById(R.id.addFriends_button_add);

    }


}
