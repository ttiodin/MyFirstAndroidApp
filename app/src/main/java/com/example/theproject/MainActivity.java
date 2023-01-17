package com.example.theproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText userTextEmail, userTextPassword;
    Button loginButton;
    FirebaseAuth mAuth;

    //This checks to see if the user is already signed in, if so they will be directed to the BaseActivity.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(), BaseActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTextEmail = findViewById(R.id.userEmail);
        userTextPassword = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.signInButton);
        mAuth =  FirebaseAuth.getInstance();
    }

    //Event Listener for Login Button and Register Text Prompt
    public void buttonOnClick(View v) {

        userTextEmail = findViewById(R.id.userEmail);
        userTextPassword = findViewById(R.id.userPassword);

        String userEmail = userTextEmail.getText().toString();
        String userPassword = userTextPassword.getText().toString();

        switch(v.getId()){
            case R.id.signInButton:
                if(userEmail.isEmpty() && userPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                } else if(userEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                } else if(userPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                } else {
                    //This is the code provided by Firebase with necessary changes.
                    mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, display a message to user and open up base Activity
                                        Toast.makeText(MainActivity.this, "Login Successful.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), BaseActivity.class);
                                        startActivity(i);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
            case R.id.registerTextPrompt:
                Intent i = new Intent(this, RegisterActivity.class);
                startActivity(i);
        }
    }
}

