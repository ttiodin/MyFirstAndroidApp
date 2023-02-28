package com.example.theproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

    //Declaring Variables
    private EditText userTextEmail, userTextPassword, userTextVerifyPassword;
    Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userTextEmail = findViewById(R.id.user_name);
        userTextPassword = findViewById(R.id.userPassword);
        userTextVerifyPassword = findViewById(R.id.userVerifyPassword);
        regButton = findViewById(R.id.registerButton);

        //When the "Create Account" button is pressed, the CreateAccount Method is called.
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    //If a user clicks the "Sign In" TextView, the user gets redirected to the MainActivity.
    public void loginOnClick(View v){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    //This is the method that is called when the "Create Account" button is clicked.
    void createAccount() {
        String email = userTextEmail.getText().toString().trim();
        String password = userTextPassword.getText().toString().trim();
        String confirmPassword = userTextVerifyPassword.getText().toString().trim();

        boolean isValid = validateInputs(email, password, confirmPassword);

        if(!isValid){
            return;
        }

        createAccountInFireBase(email, password);
    }

    //This is the method that creates the account within FireBase.
    private void createAccountInFireBase (String email, String password){
        FirebaseAuth fbAuth =  FirebaseAuth.getInstance();
        fbAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Account created.", Toast.LENGTH_SHORT).show();
                    fbAuth.getCurrentUser().sendEmailVerification();
                    fbAuth.signOut();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //This is the method that validates the inputs the user has entered (EMAIL and PASSWORDS).
    private boolean validateInputs(String email, String password, String confirmPassword) {

        if(email.isEmpty() && password.isEmpty()){
            userTextEmail.setError("Please enter an email.");
            userTextPassword.setError("Please enter a password.");
            return false;
        } else if (email.isEmpty()) {
            userTextEmail.setError("Please enter an email.");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userTextEmail.setError("Email is invalid");
            return false;
        } else if (password.isEmpty()) {
            userTextPassword.setError("Please enter a password.");
            return false;
        } else if (password.length() < 6) {
            userTextPassword.setError("Password length is invalid.");
            return false;
        } else if (confirmPassword.isEmpty() && !password.isEmpty()) {
            userTextVerifyPassword.setError("Please reenter password.");
            return false;
        } else if (!password.equals(confirmPassword)) {
            userTextVerifyPassword.setError("The passwords do not match.");
            return false;
        }
        return true;
    }
}