package com.example.theproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UtilitiesFragment extends Fragment {

    Button btnCalc, btnIncrement, btnRandomNumberGenerator, btnKgToLbs, btnCelsiusToFahrenheit;

    public UtilitiesFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_utilities, container, false);

        btnCalc = v.findViewById(R.id.btn_calculator);
        btnIncrement = v.findViewById(R.id.btn_increment_tool);
        btnRandomNumberGenerator = v.findViewById(R.id.btn_random_number);
        btnKgToLbs = v.findViewById(R.id.btn_kilograms_pounds_converter);
        btnCelsiusToFahrenheit = v.findViewById(R.id.btn_celsius_fahrenheit_converter);

        //This will open up the Calculator Activity.
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calculator = new Intent(getActivity(), Calculator.class);
                startActivity(calculator);
            }
        });

        //This opens up the increment/decrement tool.
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent incrementTool = new Intent(getActivity(), IncrementTool.class);
                startActivity(incrementTool);
            }
        });

        //This opens up the random number generator.
        btnRandomNumberGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent randomNumberGen = new Intent(getActivity(), RandomNumberGenerator.class);
                startActivity(randomNumberGen);
            }
        });

        //This opens up the Kilograms/Pounds Converter.
        btnKgToLbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kgPounds = new Intent(getActivity(), KgToLbsConverter.class);
                startActivity(kgPounds);
            }
        });

        //This opens up the Celsius/Fahrenheit Converter.
        btnCelsiusToFahrenheit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent celsiusFahrenheit = new Intent(getActivity(), CelsiusToFahrenheitConverter.class);
                startActivity(celsiusFahrenheit);
            }
        });

        return v;
    }
}