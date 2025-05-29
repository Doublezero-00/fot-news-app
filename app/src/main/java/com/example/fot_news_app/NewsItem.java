package com.example.newsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public class NewsItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String content;
    public String category;
    public long timestamp;

    public NewsItem(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.timestamp = System.currentTimeMillis();
    }
}