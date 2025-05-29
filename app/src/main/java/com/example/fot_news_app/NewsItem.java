package com.example.fot_news_app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull; // Added import

@Entity(tableName = "news")
public class NewsItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull public String title;
    @NonNull public String content;
    @NonNull public String category;
    public long timestamp;

    public NewsItem(@NonNull String title, @NonNull String content, @NonNull String category) {
        if (title == null || content == null || category == null) {
            throw new IllegalArgumentException("Title, content, and category cannot be null");
        }
        this.title = title;
        this.content = content;
        this.category = category;
        this.timestamp = System.currentTimeMillis();
    }
}