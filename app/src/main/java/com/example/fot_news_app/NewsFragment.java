package com.example.fot_news_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.example.fot_news_app.R; // Corrected import
import com.example.fot_news_app.NewsPagerAdapter; // Corrected import

public class NewsFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        // Add null checks for viewPager and tabLayout
        if (viewPager == null || tabLayout == null) {
            // Optionally, show a Toast or log an error
            // Toast.makeText(getContext(), "Error setting up news tabs.", Toast.LENGTH_SHORT).show();
            return view; // Or handle appropriately, e.g., show an error message view
        }

        viewPager.setAdapter(new NewsPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText(R.string.category_sports); break;
                case 1: tab.setText(R.string.category_academic); break;
                case 2: tab.setText(R.string.category_events); break;
                // It's good practice to have a default case, though getItemCount in adapter should prevent out-of-bounds
                default: break; 
            }
        }).attach();

        return view;
    }
}