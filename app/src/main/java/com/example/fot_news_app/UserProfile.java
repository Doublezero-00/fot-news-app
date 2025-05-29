package com.example.newsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profiles")
public class UserProfile {
    @PrimaryKey public String uid;
    public String username;
    public String email;
    public String name;

    public UserProfile(String uid, String username, String email, String name) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.name = name;
    }
}