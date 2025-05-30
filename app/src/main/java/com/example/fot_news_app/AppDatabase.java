package com.example.fot_news_app;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.example.fot_news_app.NewsItem; // Added import
import com.example.fot_news_app.UserProfile; // Added import

@Database(entities = {UserProfile.class, NewsItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "news_app_db"
                    ).fallbackToDestructiveMigration() // Added for development to handle schema changes
                     .build();
                }
            }
        }
        return INSTANCE;
    }
}