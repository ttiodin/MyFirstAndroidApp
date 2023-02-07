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
        //Button btnBack = v.findViewById(R.id.btn_back_baseActivity);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calculator = new Intent(getActivity(), Calculator.class);
                startActivity(calculator);
            }
        });

        return v;
    }
}