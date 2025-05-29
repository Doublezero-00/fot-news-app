package com.example.newsapp;

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
        editButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), EditUserInfoActivity.class));
        });

        Button signOutButton = view.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(getActivity(), SplashActivity.class));
            requireActivity().finish();
        });

        loadUserInfo();

        return view;
    }

    private void loadUserInfo() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            new Thread(() -> {
                UserProfile profile = db.appDao().getUserProfile(user.getUid());
                requireActivity().runOnUiThread(() -> {
                    if (profile != null) {
                        usernameText.setText(profile.username);
                        nameText.setText(profile.name);
                        emailText.setText(profile.email);
                    }
                });
            }).start();
        }
    }
}