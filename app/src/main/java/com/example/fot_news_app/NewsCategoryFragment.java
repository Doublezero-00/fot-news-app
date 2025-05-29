package com.example.fot_news_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fot_news_app.AppDatabase; // Added import
import com.example.fot_news_app.NewsAdapter; // Added import
import com.example.fot_news_app.NewsItem; // Added import
import com.example.fot_news_app.R; // Added import
import java.util.List; // Added import

public class NewsCategoryFragment extends Fragment {
    private static final String ARG_CATEGORY = "category";
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private AppDatabase db;
    private String category;

    public static NewsCategoryFragment newInstance(String category) {
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_category, container, false);

        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        }

        // Ensure category is not null before proceeding
        if (category == null) {
            // Optionally, log an error or set a default category
            // For now, just return the view to prevent further issues.
            // Consider showing an error message to the user.
            // Toast.makeText(getContext(), "Category not specified.", Toast.LENGTH_SHORT).show();
            return view; // Or handle appropriately
        }

        db = AppDatabase.getInstance(requireContext());
        recyclerView = view.findViewById(R.id.recycler_view);

        // Add null check for recyclerView
        if (recyclerView == null) {
            // Toast.makeText(getContext(), "Error initializing news list.", Toast.LENGTH_SHORT).show();
            return view; // Or handle appropriately
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);

        loadNews();

        return view;
    }

    private void loadNews() {
        // Ensure category is not null before attempting to load news
        if (category == null || db == null || adapter == null) {
            // Log.e("NewsCategoryFragment", "Cannot load news due to null category, db, or adapter.");
            return;
        }
        new Thread(() -> {
            final List<NewsItem> newsItems = db.appDao().getNewsByCategory(category);
            if (getActivity() != null) { // Check if activity is still available
                getActivity().runOnUiThread(() -> {
                    if (adapter != null) { // Check adapter again before use on UI thread
                        adapter.setNewsItems(newsItems);
                    }
                });
            }
        }).start();
    }
}