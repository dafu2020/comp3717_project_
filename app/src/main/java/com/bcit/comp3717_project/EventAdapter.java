package com.bcit.comp3717_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private List<Event> eventList;

    public EventAdapter(List<Event> events) {
        this.eventList = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View studentView = inflater.inflate(R.layout.item_event, parent, false);

        EventViewHolder viewHolder = new EventViewHolder(studentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        TextView tvEventName = holder.eventName;
        TextView tvEventLoc = holder.eventLocation;
        TextView tvEventDes = holder.eventDescription;
        TextView tvEventDate = holder.eventDate;
        TextView tvEventTime = holder.eventTime;
        CheckBox cbShareToFriend = holder.sharedToFriend;

        Event event = eventList.get(position);

        String eventName = event.getEventName();
        String eventLoc = event.getEventLocation();
        String eventDes = event.getEventDescription();
        String eventDate = event.getEventDate();
        String eventTime = event.getEventTime();
        boolean shareToFriend = event.isSharedToFriends();


        tvEventName.setText(eventName);
        tvEventLoc.setText(eventLoc);
        tvEventDes.setText(eventDes);
        tvEventDate.setText(eventDate);
        tvEventTime.setText(eventTime);
        cbShareToFriend.setChecked(shareToFriend);

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
