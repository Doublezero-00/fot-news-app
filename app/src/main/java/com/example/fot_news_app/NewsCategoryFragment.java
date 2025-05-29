package com.example.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        db = AppDatabase.getInstance(requireContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);

        loadNews();

        return view;
    }

    private void loadNews() {
        new Thread(() -> {
            List<NewsItem> newsItems = db.appDao().getNewsByCategory(category);
            requireActivity().runOnUiThread(() ->
                    adapter.setNewsItems(newsItems)
            );
        }).start();
    }
}