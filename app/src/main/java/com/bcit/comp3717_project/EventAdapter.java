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

        final String eventId = event.getEventID();
        final String eventName = event.getEventName();
        final String eventLoc = event.getEventLocation();
        final String eventDes = event.getEventDescription();
        final String eventDate = event.getEventDate();
        final String eventTime = event.getEventTime();
        final boolean shareToFriend = event.isSharedToFriends();


        tvEventName.setText(eventName);
        tvEventLoc.setText(eventLoc);
        tvEventDes.setText(eventDes);
        tvEventDate.setText(eventDate);
        tvEventTime.setText(eventTime);
        cbShareToFriend.setChecked(shareToFriend);


        // modify event by long-clicking
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(v.getContext(), ModifyEventActivity.class);
                intent.putExtra("eventName", eventName);
                intent.putExtra("eventLoc", eventLoc);
                intent.putExtra("eventDes", eventDes);
                intent.putExtra("eventDate", eventDate);
                intent.putExtra("eventTime", eventTime);
                intent.putExtra("shareToFriend", shareToFriend);
                intent.putExtra("eventId", eventId);

                v.getContext().startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
