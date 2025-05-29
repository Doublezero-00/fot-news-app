package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class NewsPagerAdapter extends FragmentStateAdapter {
    public NewsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return NewsCategoryFragment.newInstance("sports");
            case 1: return NewsCategoryFragment.newInstance("academic");
            case 2: return NewsCategoryFragment.newInstance("events");
            default: return NewsCategoryFragment.newInstance("sports");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}