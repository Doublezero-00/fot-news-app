package com.example.fot_news_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.fot_news_app.LoginActivity; // Added import
import com.example.fot_news_app.MainActivity; // Added import
import com.example.fot_news_app.R; // Added import

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Button getStartedButton = findViewById(R.id.get_started_button);

        // Add null check for getStartedButton
        if (getStartedButton == null) {
            // Optionally, log an error or show a toast
            // For now, just return to prevent a crash
            return;
        }

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            getStartedButton.setOnClickListener(v -> {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });
        }
    }
}