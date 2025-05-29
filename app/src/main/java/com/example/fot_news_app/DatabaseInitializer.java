package com.example.fot_news_app;

import com.example.fot_news_app.AppDatabase; // Added import
import com.example.fot_news_app.NewsItem; // Added import

public class DatabaseInitializer {
    public static void initialize(AppDatabase db) {
        if (db == null) {
            // Log.e("DatabaseInitializer", "AppDatabase instance is null. Cannot initialize data.");
            return;
        }
        new Thread(() -> {
            // It's also good practice to ensure db.appDao() isn't null,
            // though with Room setup this is highly unlikely unless db itself is a mock/bad state.
            if (db.appDao() == null) {
                // Log.e("DatabaseInitializer", "AppDao is null. Cannot initialize data.");
                return;
            }
            db.appDao().clearAllNews();

            // Sports news
            db.appDao().insertNewsItem(new NewsItem(
                    "Football Tournament",
                    "Annual inter-department football tournament starts next week",
                    "sports"));

            db.appDao().insertNewsItem(new NewsItem(
                    "Basketball Finals",
                    "Engineering vs Science basketball finals this Friday",
                    "sports"));

            // Academic news
            db.appDao().insertNewsItem(new NewsItem(
                    "Midterm Exams",
                    "Midterm exam schedule has been published",
                    "academic"));

            db.appDao().insertNewsItem(new NewsItem(
                    "Library Hours",
                    "Library will be open 24/7 during exam period",
                    "academic"));

            // Events news
            db.appDao().insertNewsItem(new NewsItem(
                    "Cultural Festival",
                    "Annual cultural festival preparations begin",
                    "events"));

            db.appDao().insertNewsItem(new NewsItem(
                    "Career Fair",
                    "Over 50 companies attending next month's career fair",
                    "events"));
        }).start();
    }
}