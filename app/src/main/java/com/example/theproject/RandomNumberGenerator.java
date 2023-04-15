package com.example.theproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class RandomNumberGenerator extends AppCompatActivity {

    private TextView randomNumber;
    private Button generateButton, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_number_generator);

        randomNumber = findViewById(R.id.random_number);
        generateButton = findViewById(R.id.generate_button);
        backBtn = findViewById(R.id.backBtn);

        //On Click Listener to Generate a Random Number when clicking the button.
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int val = random.nextInt(1000);
                randomNumber.setText(Integer.toString(val));
            }
        });

        //Back button to go back to the Base Activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent baseActivity = new Intent(RandomNumberGenerator.this, BaseActivity.class);
                startActivity(baseActivity);
            }
        });
    }
}