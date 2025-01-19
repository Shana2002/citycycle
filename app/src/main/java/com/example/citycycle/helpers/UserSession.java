package com.example.citycycle.helpers;

import com.example.citycycle.models.User;

public class UserSession {
    private static UserSession instance;
    private User user;

    public UserSession(){}

    public static synchronized UserSession getInstance(){
        if (instance == null){
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return  user;
    }

    public void clearSession(){
        user = null;
    }
}
