package com.example.theproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        EditText user = findViewById(R.id.userName);
        EditText password = findViewById(R.id.userPassword);
        Button logInButton = findViewById(R.id.signInButton);
        TextView registerButton = findViewById(R.id.registerTextPrompt);

        logInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                String userName = user.getText().toString();
                String userPassword = password.getText().toString();

                if(userName.isEmpty() || userPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter username or password.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "LOREM IPSUM", Toast.LENGTH_SHORT).show();
                }
            }
        }); */
    }

    //Event Listener for Login Button and Register Text Prompt
    public void buttonOnClick(View v) {

        EditText email = findViewById(R.id.userEmail);
        EditText password = findViewById(R.id.userPassword);
        //Button logInButton = findViewById(R.id.signInButton);
        //TextView registerButton = findViewById(R.id.registerTextPrompt);

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        switch(v.getId()){
            case R.id.signInButton:
                if(userEmail.isEmpty() || userPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter email or password.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "LOREM IPSUM", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.registerTextPrompt:
                Intent i = new Intent(this, RegisterActivity.class);
                //String message = ((TextView)findViewById(R.id.registerTextPrompt)).getText().toString();
                //i.putExtra("COOL", message);
                startActivity(i);

                //Toast.makeText(MainActivity.this, "CODE TO OPEN REGISTER ACTIVITY.", Toast.LENGTH_SHORT).show();
        }
    }
}

