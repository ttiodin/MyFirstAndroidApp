package com.example.theproject;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class KgToLbsConverter extends AppCompatActivity {

    private EditText editedUnit, convertedValue;
    private TextView editedUnitLabel, convertedValueLabel;
    private final double conversionRate = 2.20462;
    private Button backBtn;
    private Switch unitSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kg_to_lbs_converter);

        editedUnit = findViewById(R.id.unit_edited);
        convertedValue = findViewById(R.id.converted_value);
        backBtn = findViewById(R.id.btn_back_baseActivity);
        unitSwitcher = findViewById(R.id.unit_switcher);
        editedUnitLabel = findViewById(R.id.unit_edited_label);
        convertedValueLabel = findViewById(R.id.converted_value_label);

        //This is the code for when the user changes the unit being edited.
        editedUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                KilogramsToPounds();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //This is the code that executes based on the selection of the Switch.
        unitSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(unitSwitcher.isChecked()) {
                    editedUnitLabel.setText("Pounds");
                    convertedValueLabel.setText("Kilograms");
                    editedUnit.setText("0");
                    convertedValue.setText("0");

                    //This is the code for when the user changes the unit being edited.
                    editedUnit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            PoundsToKilograms();
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                } else {
                    editedUnitLabel.setText("Kilograms");
                    convertedValueLabel.setText("Pounds");
                    editedUnit.setText("0");
                    convertedValue.setText("0");

                    //This is the code for when the user changes the unit being edited.
                    editedUnit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            KilogramsToPounds();
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
            }
        });

        //This is the code to go back to the BaseActivity (Main Screen).
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent baseActivity = new Intent(KgToLbsConverter.this, BaseActivity.class);
                startActivity(baseActivity);
            }
        });
    }

    public void KilogramsToPounds () {
        if(!editedUnit.getText().toString().isEmpty()){
            double kilogramValue = parseDouble(editedUnit.getText().toString());
            double newKilogramValue = kilogramValue * conversionRate;
            convertedValue.setText(String.format("%.4f", newKilogramValue));
        } else {
            convertedValue.setText("");
        }
    }

    public void PoundsToKilograms() {
        if(!editedUnit.getText().toString().isEmpty()){
            double enteredValue = parseDouble(editedUnit.getText().toString());
            double newConvertedValue = enteredValue / conversionRate;
            convertedValue.setText(String.format("%.4f", newConvertedValue));
        } else {
            convertedValue.setText("");
        }
    }
}