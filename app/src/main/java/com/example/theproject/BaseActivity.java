package com.example.theproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button logButton;
    TextView userEmail;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        auth = FirebaseAuth.getInstance();
        logButton = findViewById(R.id.logoutButton);
        userEmail = findViewById(R.id.userInfo);
        user = auth.getCurrentUser();
        if (user == null){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        } else {
             userEmail.setText(user.getEmail());
        }

        //On Click Listener for the Button
        logButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //Signs the User out of FireBase
                FirebaseAuth.getInstance().signOut();

                //When the user signs out, a Toast is shown and the user will be redirected to the Log In Screen.
                Toast.makeText(BaseActivity.this, "Successfully Logged Out.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}