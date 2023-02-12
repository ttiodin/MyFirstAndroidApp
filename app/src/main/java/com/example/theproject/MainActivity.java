package com.example.theproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    private TextView registerPrompt;
    Button loginButton;
    private FirebaseAuth mAuth;
    ImageView btnGitHub, btnGoogle;

    //Variables for Google SignNn
    private GoogleSignInClient gsc;
    private final static int RC_SIGN_IN = 123;

    //This checks to see if the user is already signed in, if so they will be directed to the BaseActivity.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            Intent baseActivity = new Intent(getApplicationContext(), BaseActivity.class);
            startActivity(baseActivity);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTextEmail = findViewById(R.id.user_name);
        userTextPassword = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.signInButton);
        registerPrompt = findViewById(R.id.registerTextPrompt);
        btnGoogle = findViewById(R.id.google_signIn_logo);
        btnGitHub = findViewById(R.id.github_signIn_logo);
        mAuth =  FirebaseAuth.getInstance();

        createRequest();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        //When the user clicks "Register Now" they will be prompted to RegisterActivity.
        registerPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

        //Google Button OnClickListener that runs method.
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignIn();
            }
        });

        //GitHub ImageView On Click Listener that launches GitHubAuthentication Activity.
        btnGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent baseActivity = new Intent(MainActivity.this, GitHubAuthentication.class);
                startActivity(baseActivity);
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

    //This method runs when the google ImageView is clicked.
    private void GoogleSignIn() {
        Intent signInGoogleIntent = gsc.getSignInIntent();
        startActivityForResult(signInGoogleIntent, RC_SIGN_IN);
    }

    //This method is called in the GoogleSignIn Method, if the Request Code is correct, Prompts Google Sign In Page.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
        }
    }

    //This is the method that verifies the google account being used to sign in.
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent baseActivity = new Intent(getApplicationContext(), BaseActivity.class);
                            startActivity(baseActivity);
                        } else {
                            Toast.makeText(MainActivity.this, "Sorry Auth Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //This is the method that logs in the user via email and password.
    private void loginUser() {
        String email = userTextEmail.getText().toString().trim();
        String password = userTextPassword.getText().toString().trim();

        boolean isValid = validateInputs(email, password);

        if(!isValid){
            return;
        }

        signInAccountInFireBase(email, password);
    }

    //This is the method that logins the account within FireBase.
    private void signInAccountInFireBase (String email, String password){
        FirebaseAuth fbAuth =  FirebaseAuth.getInstance();
        fbAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if(fbAuth.getCurrentUser().isEmailVerified()) {
                        Toast.makeText(MainActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, BaseActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Email not verified, Please verify your email.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //This is the method that validates the inputs the user has entered (EMAIL and PASSWORD).
    private boolean validateInputs(String email, String password) {

        if(email.isEmpty() && password.isEmpty()){
            userTextEmail.setError("Please enter an email.");
            userTextPassword.setError("Please enter a password.");
            return false;
        } else if (email.isEmpty()) {
            userTextEmail.setError("Please enter an email.");
            return false;
        } else if (password.isEmpty()) {
            userTextPassword.setError("Please enter a password.");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userTextEmail.setError("Email is invalid");
            return false;
        } else if (password.length() < 6) {
            userTextPassword.setError("Password length is invalid.");
            return false;
        }
        return true;
    }
}

