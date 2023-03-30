package com.example.theproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IncrementTool extends AppCompatActivity {

    //Creating Variables
    private TextView value;
    private Button increment, decrement;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increment_tool);

        value = findViewById(R.id.incremental_value);
        increment = findViewById(R.id.increment_button);
        decrement = findViewById(R.id.decrement_button);

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIncrement();
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDecrement();
            }
        });
    }

    private void setIncrement() {
        count++;
        value.setText(String.valueOf(count));
    }

    private void setDecrement() {
        count--;
        value.setText(String.valueOf(count));
    }
}