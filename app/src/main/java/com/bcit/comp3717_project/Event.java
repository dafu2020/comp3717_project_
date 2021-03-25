package com.bcit.comp3717_project;

public class Event {

    private String eventName, eventLocation, eventDescription, eventDate, eventTime, eventID;
    private boolean sharedToFriends;


    public Event(){}

    public Event(String eventName,
                 String eventLocation,
                 String eventDescription,
                 String eventDate,
                 String eventTime,
                 boolean sharedToFriends,
                 String eventID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.sharedToFriends = sharedToFriends;

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public boolean isSharedToFriends() {
        return sharedToFriends;
    }

    public void setSharedToFriends(boolean sharedToFriends) {
        this.sharedToFriends = sharedToFriends;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
