package com.example.newsapp;

public class DatabaseInitializer {
    public static void initialize(AppDatabase db) {
        new Thread(() -> {
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