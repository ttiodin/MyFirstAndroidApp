package com.example.theproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UtilitiesFragment extends Fragment {

    public UtilitiesFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_utilities, container, false);

        Button btnCalc = v.findViewById(R.id.btn_calculator);
        Button btnIncrement = v.findViewById(R.id.btn_increment_tool);
        Button btnRandomNumberGenerator = v.findViewById(R.id.btn_random_number);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calculator = new Intent(getActivity(), Calculator.class);
                startActivity(calculator);
            }
        });

        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent incrementTool = new Intent(getActivity(), IncrementTool.class);
                startActivity(incrementTool);
            }
        });

        btnRandomNumberGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent randomNumberGen = new Intent(getActivity(), RandomNumberGenerator.class);
                startActivity(randomNumberGen);
            }
        });

        return v;
    }
}