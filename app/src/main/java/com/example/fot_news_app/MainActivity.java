package com.example.fot_news_app;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.fot_news_app.AppDatabase; // Added import
import com.example.fot_news_app.R; // Added import
import com.example.fot_news_app.SplashActivity; // Added import
import com.example.fot_news_app.UserProfile; // Added import
import android.content.Intent; // Added import
import android.view.View; // Added import
import androidx.appcompat.widget.Toolbar; // Added import

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Update header with user info
        View headerView = navigationView.getHeaderView(0);
        TextView navName = headerView.findViewById(R.id.nav_header_name);
        TextView navEmail = headerView.findViewById(R.id.nav_header_email);

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) { // Add null check for user
            if (user.getEmail() != null) { // Add null check for email
                navEmail.setText(user.getEmail());
            }
            if (user.getUid() != null) { // Add null check for UID
                AppDatabase db = AppDatabase.getInstance(this);
                new Thread(() -> {
                    UserProfile profile = db.appDao().getUserProfile(user.getUid());
                    runOnUiThread(() -> {
                        if (profile != null) {
                            navName.setText(profile.name);
                        }
                    });
                }).start();
            }
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_news, R.id.nav_user_info, R.id.nav_dev_info)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_sign_out) {
            auth.signOut();
            startActivity(new Intent(this, SplashActivity.class));
            finish();
            return true;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}