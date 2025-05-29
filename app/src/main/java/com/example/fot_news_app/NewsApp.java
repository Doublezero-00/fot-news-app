package com.example.newsapp;

import android.app.Application;

public class NewsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase db = AppDatabase.getInstance(this);
        DatabaseInitializer.initialize(db);
    }
}