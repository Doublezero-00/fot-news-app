package com.example.fot_news_app;

import android.app.Application;
import com.example.fot_news_app.AppDatabase; // Added import
import com.example.fot_news_app.DatabaseInitializer; // Added import

public class NewsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase db = AppDatabase.getInstance(this);
        DatabaseInitializer.initialize(db);
    }
}