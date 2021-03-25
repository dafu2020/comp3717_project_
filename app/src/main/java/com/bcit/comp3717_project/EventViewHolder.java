package com.bcit.comp3717_project;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {

    public TextView eventName, eventLocation, eventDescription, eventDate, eventTime;
    public CheckBox sharedToFriend;


    public EventViewHolder(@NonNull View itemView) {
        super(itemView);

        eventName = itemView.findViewById(R.id.itemEvent_tv_eventName);
        eventLocation = itemView.findViewById(R.id.itemEvent_tv_location);
        eventDescription= itemView.findViewById(R.id.itemEvent_tv_description);
        eventDate = itemView.findViewById(R.id.itemEvent_tv_date);
        eventTime = itemView.findViewById(R.id.itemEvent_tv_time);
        sharedToFriend = itemView.findViewById(R.id.itemEvent_checkBox);

    }
}
