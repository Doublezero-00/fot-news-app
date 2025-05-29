package com.example.newsapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface AppDao {
    // User operations
    @Insert
    void insertUserProfile(UserProfile userProfile);

    @Query("SELECT * FROM user_profiles WHERE uid = :uid")
    UserProfile getUserProfile(String uid);

    // News operations
    @Insert
    void insertNewsItem(NewsItem newsItem);

    @Query("SELECT * FROM news WHERE category = :category ORDER BY timestamp DESC")
    List<NewsItem> getNewsByCategory(String category);

    @Query("DELETE FROM news")
    void clearAllNews();
}