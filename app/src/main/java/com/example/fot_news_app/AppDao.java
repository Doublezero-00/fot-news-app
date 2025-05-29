package com.example.fot_news_app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.fot_news_app.NewsItem; // Added import
import com.example.fot_news_app.UserProfile; // Added import
import java.util.List;

@Dao
public interface AppDao {
    // User operations
import androidx.annotation.NonNull; // Added import

@Dao
public interface AppDao {
    // User operations
    @Insert
    void insertUserProfile(@NonNull UserProfile userProfile);

    @Query("SELECT * FROM user_profiles WHERE uid = :uid")
    UserProfile getUserProfile(@NonNull String uid);

    // News operations
    @Insert
    void insertNewsItem(@NonNull NewsItem newsItem);

    @Query("SELECT * FROM news WHERE category = :category ORDER BY timestamp DESC")
    List<NewsItem> getNewsByCategory(@NonNull String category);

    @Query("DELETE FROM news")
    void clearAllNews();
}