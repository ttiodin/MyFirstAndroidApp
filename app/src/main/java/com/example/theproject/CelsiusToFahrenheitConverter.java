package com.example.theproject;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CelsiusToFahrenheitConverter extends AppCompatActivity {

    private EditText originalTemperature;
    private TextView convertedTemperature;
    private Button btnCelsiusToFahrenheit, btnFahrenheitToCelsius, backBtn;
    private double initialTemp, convertedTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celcius_to_fahrenheit_converter);

        originalTemperature = findViewById(R.id.initial_temperature);
        convertedTemperature = findViewById(R.id.converted_temperature);
        btnCelsiusToFahrenheit = findViewById(R.id.btn_celsius_to_fahrenheit);
        btnFahrenheitToCelsius = findViewById(R.id.btn_fahrenheit_to_celsius);
        backBtn = findViewById(R.id.btn_back_baseActivity);

        //This is the code that executes if the user wants to convert Celsius to Fahrenheit.
        btnCelsiusToFahrenheit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!originalTemperature.getText().toString().isEmpty()){
                    initialTemp = parseDouble(originalTemperature.getText().toString());
                    convertedTemp = (initialTemp * 9)/5 + 32;
                    convertedTemperature.setText(String.valueOf(initialTemp) + "°C is " + String.valueOf(convertedTemp) + "°F");
                    convertedTemperature.setVisibility(View.VISIBLE);
                }
            }
        });

        //This is the code that executes if the user wants to convert Fahrenheit to Celsius.
        btnFahrenheitToCelsius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!originalTemperature.getText().toString().isEmpty()){
                    initialTemp = parseDouble(originalTemperature.getText().toString());
                    convertedTemp = (initialTemp - 32) * 5 / 9;
                    convertedTemperature.setText(String.valueOf(initialTemp) + "°F is " + String.valueOf(String.format("%.2f", convertedTemp)) + "°C");
                    convertedTemperature.setVisibility(View.VISIBLE);
                }
            }
        });

        //This is the code to go back to the BaseActivity (Main Screen).
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent baseActivity = new Intent(CelsiusToFahrenheitConverter.this, BaseActivity.class);
                startActivity(baseActivity);
            }
        });
    }
}