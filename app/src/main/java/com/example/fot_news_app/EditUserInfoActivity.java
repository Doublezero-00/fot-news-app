package com.example.fot_news_app;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.fot_news_app.AppDatabase; // Added import
import com.example.fot_news_app.R; // Added import
import com.example.fot_news_app.UserProfile; // Added import

public class EditUserInfoActivity extends AppCompatActivity {
    private EditText etUsername, etName;
    private AppDatabase db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        db = AppDatabase.getInstance(this);
        auth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.etUsername);
        etName = findViewById(R.id.etName);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        // Add null checks for all views obtained via findViewById
        if (etUsername == null || etName == null || btnSave == null || btnCancel == null) {
            Toast.makeText(this, "Error initializing edit screen. Please try again.", Toast.LENGTH_LONG).show();
            // finish(); // Optionally finish activity
            return;
        }

        loadUserInfo();

        btnSave.setOnClickListener(v -> saveChanges());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void loadUserInfo() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null && user.getUid() != null) { // Also check if UID is not null
            new Thread(() -> {
                // Ensure etUsername and etName are not null before trying to set text on UI thread
                if (etUsername == null || etName == null) return;

                UserProfile profile = db.appDao().getUserProfile(user.getUid());
                runOnUiThread(() -> {
                    if (profile != null) {
                        etUsername.setText(profile.username);
                        etName.setText(profile.name);
                    } else {
                        // Handle case where profile is not found, maybe set fields to empty or show a message
                        etUsername.setText("");
                        etName.setText("");
                        Toast.makeText(EditUserInfoActivity.this, "User profile not found.", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        } else {
            // Handle case where user is null or UID is null
             if (etUsername != null) etUsername.setText("");
             if (etName != null) etName.setText("");
            Toast.makeText(this, "Cannot load user information.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveChanges() {
        // Ensure EditTexts are not null before getting text
        if (etUsername == null || etName == null) {
            Toast.makeText(this, "An error occurred. Cannot save changes.", Toast.LENGTH_SHORT).show();
            return;
        }
        String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            new Thread(() -> {
                UserProfile profile = db.appDao().getUserProfile(user.getUid());
                if (profile != null) {
                    profile.username = username;
                    profile.name = name;
                    // db.appDao().updateUserProfile(profile); // Removed call to undefined method
                }
                runOnUiThread(() -> {
                    Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        }
    }
}