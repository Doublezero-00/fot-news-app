package com.example.fot_news_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.fot_news_app.AppDatabase; // Added import
import com.example.fot_news_app.EditUserInfoActivity; // Added import
import com.example.fot_news_app.R; // Added import
import com.example.fot_news_app.SplashActivity; // Added import
import com.example.fot_news_app.UserProfile; // Added import

public class UserInfoFragment extends Fragment {
    private TextView usernameText, nameText, emailText;
    private AppDatabase db;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        db = AppDatabase.getInstance(requireContext());
        auth = FirebaseAuth.getInstance();

        usernameText = view.findViewById(R.id.username_text);
        nameText = view.findViewById(R.id.name_text);
        emailText = view.findViewById(R.id.email_text);

        Button editButton = view.findViewById(R.id.edit_button);
        Button signOutButton = view.findViewById(R.id.sign_out_button);

        // Add null checks for TextViews and Buttons
        if (usernameText == null || nameText == null || emailText == null || editButton == null || signOutButton == null) {
            // Toast.makeText(getContext(), "Error initializing user info screen.", Toast.LENGTH_LONG).show();
            // Depending on severity, might not want to proceed further with this view
            return view; // Or handle appropriately
        }

        editButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                startActivity(new Intent(getActivity(), EditUserInfoActivity.class));
            }
        });

        signOutButton.setOnClickListener(v -> {
            auth.signOut();
            if (getActivity() != null) {
                startActivity(new Intent(getActivity(), SplashActivity.class));
                getActivity().finish(); // Use getActivity() for consistency, ensure it's not null
            }
        });

        loadUserInfo();

        return view;
    }

    private void loadUserInfo() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null && user.getUid() != null) { // Check user and UID
            new Thread(() -> {
                // Ensure TextViews are not null before trying to set text on UI thread
                if (usernameText == null || nameText == null || emailText == null) return;

                final UserProfile profile = db.appDao().getUserProfile(user.getUid());
                if (getActivity() != null) { // Check activity before runOnUiThread
                    getActivity().runOnUiThread(() -> {
                        if (profile != null) {
                            usernameText.setText(profile.username);
                            nameText.setText(profile.name);
                            emailText.setText(profile.email);
                        } else {
                            // Handle profile not found - clear fields or show placeholder
                            usernameText.setText("N/A");
                            nameText.setText("User details not found");
                            emailText.setText("");
                            // Toast.makeText(getContext(), "Could not load user profile.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        } else {
            // Handle user not logged in or UID is null - clear fields or show placeholder
            if (usernameText != null) usernameText.setText("Not logged in");
            if (nameText != null) nameText.setText("");
            if (emailText != null) emailText.setText("");
            // Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
}