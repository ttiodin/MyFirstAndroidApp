package com.example.theproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private EditText userTextEmail, userTextPassword;
    Button loginButton;
    private FirebaseAuth mAuth;

    //Variables for Google SignNn
    private GoogleSignInClient gsc;
    private final static int RC_SIGN_IN = 123;

    //This checks to see if the user is already signed in, if so they will be directed to the BaseActivity.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent baseActivity = new Intent(getApplicationContext(), BaseActivity.class);
            startActivity(baseActivity);
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

        createRequest();

        findViewById(R.id.google_signin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignIn();
            }
        });
    }

    //This is a method used to create a request for the email to google.
    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Build Google Sign In Client with Options
        gsc = GoogleSignIn.getClient(this, gso);
    }

    private void GoogleSignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent baseActivity = new Intent(getApplicationContext(), BaseActivity.class);
                            startActivity(baseActivity);
                        } else {
                            Toast.makeText(MainActivity.this, "Sorry Auth Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Event Listener for Login Button and Register Text Prompt
    public void buttonOnClick(View v) {

        userTextEmail = findViewById(R.id.userEmail);
        userTextPassword = findViewById(R.id.userPassword);

        String userEmail = userTextEmail.getText().toString().trim();
        String userPassword = userTextPassword.getText().toString().trim();

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
                                        Intent baseActivity = new Intent(getApplicationContext(), BaseActivity.class);
                                        startActivity(baseActivity);
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
                Intent registerActivity = new Intent(this, RegisterActivity.class);
                startActivity(registerActivity);
        }
    }
}

