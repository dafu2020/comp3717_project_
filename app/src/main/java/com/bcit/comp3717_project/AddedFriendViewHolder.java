package com.bcit.comp3717_project;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddedFriendViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTV;
    public TextView emailTV;


    public AddedFriendViewHolder(@NonNull View itemView) {
        super(itemView);

        nameTV = itemView.findViewById(R.id.item_friend_tv_name);
        emailTV = itemView.findViewById(R.id.item_friend_tv_email);
    }
}
