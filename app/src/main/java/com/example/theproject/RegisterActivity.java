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

public class RegisterActivity extends AppCompatActivity {

    EditText userTextEmail, userTextPassword;
    Button regButton;
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
        setContentView(R.layout.activity_register);

        userTextEmail = findViewById(R.id.userEmail);
        userTextPassword = findViewById(R.id.userPassword);
        regButton = findViewById(R.id.registerButton);
        mAuth =  FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email, password;
                email = userTextEmail.getText().toString();
                password = userTextPassword.getText().toString();

                if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                } else {
                    //This is the code provided by FireBase with necessary changes.
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegisterActivity.this, "Account created.",
                                                Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(getApplicationContext(), BaseActivity.class);
                                        startActivity(i);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public void loginOnClick(View v){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}