package com.example.theproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userEmail, userName;
    private Uri photoUrl;
    private BottomNavigationView bottomNavView;

    final private HomeFragment homeFragment = new HomeFragment();
    final private UtilitiesFragment utilitiesFragment = new UtilitiesFragment();
    final private SettingsFragment settingsFragment = new SettingsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        /*
         * If a user is still authenticated through FireBase this activity will remain, if not the user will be
         * redirected to the MainActivity (Login Page).
         */
        if (user == null){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        } else {
             //userEmail.setText(user.getEmail());
            userEmail = user.getEmail();
            userName = user.getDisplayName();
            photoUrl = user.getPhotoUrl();
        }

        bottomNavView = findViewById(R.id.bottom_navigation_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).commit();
        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).commit();
                        return true;
                    case R.id.nav_utilities:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,utilitiesFragment).commit();
                        return true;
                    case R.id.nav_settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    public void logOut(View v) {
        //Signs the User out of FireBase
        auth.getInstance().signOut();

        //When the user signs out, a Toast is shown and the user will be redirected to the Log In Screen.
        Toast.makeText(this, "Successfully Logged Out.", Toast.LENGTH_SHORT).show();
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
    }
}