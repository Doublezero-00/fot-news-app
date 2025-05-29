package com.example.fot_news_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.fot_news_app.NewsItem; // Added import
import com.example.fot_news_app.R; // Added import

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsItem> newsItems = new ArrayList<>();

    public void setNewsItems(List<NewsItem> newNewsItems) {
        if (newNewsItems != null) {
            this.newsItems.clear();
            this.newsItems.addAll(newNewsItems);
        } else {
            this.newsItems.clear(); // Or handle as an error, or ignore
        }
        notifyDataSetChanged(); // Consider using DiffUtil for better performance
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        // newsItems list is guaranteed non-null by initialization and setNewsItems logic.
        // Also, position is always valid as per getItemCount.
        NewsItem item = newsItems.get(position);

        // item itself and its fields title/content are @NonNull from NewsItem class
        if (holder.titleText != null) {
            holder.titleText.setText(item.title);
        }
        if (holder.contentText != null) {
            holder.contentText.setText(item.content);
        }
    }

    @Override
    public int getItemCount() {
        // newsItems is guaranteed non-null
        return newsItems.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, contentText;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.news_title);
            contentText = itemView.findViewById(R.id.news_content);

            // It's good practice to check if findViewById returned null,
            // though in onBindViewHolder we are checking before setting text.
            // if (titleText == null) { Log.e("NewsViewHolder", "titleText not found"); }
            // if (contentText == null) { Log.e("NewsViewHolder", "contentText not found"); }
        }
    }
}