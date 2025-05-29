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
import com.example.fot_news_app.MainActivity; // Added import
import com.example.fot_news_app.R; // Added import
import com.example.fot_news_app.RegistrationActivity; // Added import

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Add null checks for all views obtained via findViewById
        if (etEmail == null || etPassword == null || btnLogin == null || tvRegister == null || tvForgotPassword == null) {
            Toast.makeText(this, "Error initializing login screen. Please try again.", Toast.LENGTH_LONG).show();
            // Depending on the severity, you might want to finish the activity
            // finish(); 
            return;
        }

        btnLogin.setOnClickListener(v -> {
            // It's safer to get text inside the listener, in case etEmail/etPassword were null initially
            // but this is largely covered by the check above.
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Password is required");
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            String errorMessage = "Login failed.";
                            if (task.getException() != null) {
                                errorMessage += " " + task.getException().getMessage();
                            }
                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });

        tvForgotPassword.setOnClickListener(v -> {
            // Implement password reset functionality
            Toast.makeText(this, "Reset password clicked", Toast.LENGTH_SHORT).show();
        });
    }
}