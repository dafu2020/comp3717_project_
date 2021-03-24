package com.bcit.comp3717_project;

import java.util.ArrayList;
import java.util.List;

public class User {
   public String name, email;

    private List<User> friendList;
    private List<User> eventList;


    public User(){
    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
        this.friendList = new ArrayList<User>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }
}
