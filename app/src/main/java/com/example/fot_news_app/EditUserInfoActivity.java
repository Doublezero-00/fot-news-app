package com.example.newsapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        loadUserInfo();

        btnSave.setOnClickListener(v -> saveChanges());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void loadUserInfo() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            new Thread(() -> {
                UserProfile profile = db.appDao().getUserProfile(user.getUid());
                runOnUiThread(() -> {
                    if (profile != null) {
                        etUsername.setText(profile.username);
                        etName.setText(profile.name);
                    }
                });
            }).start();
        }
    }

    private void saveChanges() {
        String username = etUsername.getText().toString().trim();
        String name = etName.getText().toString().trim();

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
                    db.appDao().updateUserProfile(profile);
                }
                runOnUiThread(() -> {
                    Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        }
    }
}