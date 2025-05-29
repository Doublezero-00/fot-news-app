package com.example.fot_news_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.fot_news_app.AppDatabase; // Added import
import com.example.fot_news_app.MainActivity; // Added import
import com.example.fot_news_app.R; // Added import
import com.example.fot_news_app.UserProfile; // Added import

public class RegistrationActivity extends AppCompatActivity {
    private TextInputEditText etUsername, etEmail, etPassword, etConfirmPassword;
    private FirebaseAuth auth;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        db = AppDatabase.getInstance(this);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvLogin = findViewById(R.id.tvLogin);

        // Add null checks for all views obtained via findViewById
        if (etUsername == null || etEmail == null || etPassword == null || etConfirmPassword == null || btnRegister == null || tvLogin == null) {
            Toast.makeText(this, "Error initializing registration screen. Please try again.", Toast.LENGTH_LONG).show();
            // finish(); // Optionally finish activity
            return;
        }

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            String password = etPassword.getText() != null ? etPassword.getText().toString() : "";
            String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString() : "";

            if (TextUtils.isEmpty(username)) {
                etUsername.setError("Username is required");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Password is required");
                return;
            }

            if (password.length() < 6) {
                etPassword.setError("Password must be at least 6 characters");
                return;
            }

            if (!password.equals(confirmPassword)) {
                etConfirmPassword.setError("Passwords don't match");
                return;
            }

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                UserProfile profile = new UserProfile(
                                        user.getUid(),
                                        username,
                                        email,
                                        username);

                                new Thread(() -> {
                                    db.appDao().insertUserProfile(profile);
                                    runOnUiThread(() -> {
                                        Toast.makeText(this, "Registration successful",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(this, MainActivity.class));
                                        finish();
                                    });
                                }).start();
                            } else {
                                // Handle case where user is null after successful creation (should not happen)
                                Toast.makeText(this, "Registration successful, but failed to get user info.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String errorMessage = "Registration failed.";
                            if (task.getException() != null) {
                                errorMessage += " " + task.getException().getMessage();
                            }
                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        tvLogin.setOnClickListener(v -> {
            finish();
        });
    }
}