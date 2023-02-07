package com.example.theproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Calculator extends AppCompatActivity implements View.OnClickListener {

    private TextView result, solution;
    Button btnC, btnAC, btnBracketOpen, btnBracketClose, btnBack,
            btnDivide, btnMultiply, btnAdd, btnSubtract,btnEquals, btnDecimal,
            btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        result = findViewById(R.id.result);
        solution = findViewById(R.id.solution);
        btnBack = findViewById(R.id.btn_back_baseActivity);


        assignID(btnC,R.id.button_clear);
        assignID(btnBracketOpen,R.id.button_open_bracket_left);
        assignID(btnBracketClose,R.id.button_open_bracket_right);
        assignID(btnMultiply,R.id.button_multiply);
        assignID(btnDivide,R.id.button_divide);
        assignID(btnAdd,R.id.button_add);
        assignID(btnSubtract,R.id.button_subtract);
        assignID(btnEquals,R.id.button_equals);
        assignID(btn0,R.id.button_zero);
        assignID(btn1,R.id.button_one);
        assignID(btn2,R.id.button_two);
        assignID(btn3,R.id.button_three);
        assignID(btn4,R.id.button_four);
        assignID(btn5,R.id.button_five);
        assignID(btn6,R.id.button_six);
        assignID(btn7,R.id.button_seven);
        assignID(btn8,R.id.button_eight);
        assignID(btn9,R.id.button_nine);
        assignID(btnAC,R.id.button_allClear);
        assignID(btnDecimal,R.id.button_decimal);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent baseActivity = new Intent(Calculator.this, BaseActivity.class);
                startActivity(baseActivity);
            }
        });

    }

    void assignID(Button btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solution.getText().toString();

        if(buttonText.equals("A/C")){
            solution.setText("");
            result.setText("0");
            return;
        } else if (buttonText.equals("=")) {
            solution.setText(result.getText());
            return;
        } else if (buttonText.equals("C")) {

            if (dataToCalculate.length() > 1) {
                dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
            } else {
                dataToCalculate = "0";
            }

        } else {
            dataToCalculate = dataToCalculate+buttonText;
        }

        solution.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("Err")) {
            result.setText(finalResult);
        }
    }

    public String getResult (String data) {
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"JavaScript", 1, null).toString();
            if(finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }

    }
}