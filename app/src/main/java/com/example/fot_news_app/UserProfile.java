package com.example.fot_news_app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull; // Added import

@Entity(tableName = "user_profiles")
public class UserProfile {
    @PrimaryKey @NonNull public String uid; // uid should not be null
    @NonNull public String username;
    @NonNull public String email;
    @NonNull public String name;

    public UserProfile(@NonNull String uid, @NonNull String username, @NonNull String email, @NonNull String name) {
        if (uid == null || username == null || email == null || name == null) {
            throw new IllegalArgumentException("User profile fields cannot be null");
        }
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.name = name;
    }
}