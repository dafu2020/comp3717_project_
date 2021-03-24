package com.bcit.comp3717_project;

import java.util.ArrayList;

public class User {
   public String name, email;

    ArrayList<User> friendList = new ArrayList<User>();

    public User(){

    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }


}
