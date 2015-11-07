package com.example.shadman.calculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.StringTokenizer;
import com.fathzer.soft.javaluator.DoubleEvaluator;

public class MainActivity extends AppCompatActivity {
    EditText display;
    DoubleEvaluator calc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        display = (EditText) findViewById(R.id.textViewDisplay);
    }

    public void GetNum(View v) {
        switch (v.getId()) {
            case R.id.btnZero:
                display.append("0");
                break;
            case R.id.btnOne:
                display.append("1");
                break;
            case R.id.btnTwo:
                display.append("2");
                break;
            case R.id.btnThree:
                display.append("3");
                break;
            case R.id.btnFour:
                display.append("4");
                break;
            case R.id.btnFive:
                display.append("5");
                break;
            case R.id.btnSix:
                display.append("6");
                break;
            case R.id.btnSeven:
                display.append("7");
                break;
            case R.id.btnEight:
                display.append("8");
                break;
            case R.id.btnNine:
                display.append("9");
                break;

        }
    }

    public void GetDot(View v) {
        if(dotIsValid(display.getText().toString())) {
            display.append(".");
        }
    }

    public void GetSub(View v) {
        String str = display.getText().toString();
        if(str.isEmpty() && !isOperatorValid(str)) {
            display.append("-");
        } else {
            if (isOperatorValid(str)) {
                display.append("-");
            }
        }
    }

    public void GetOperator (View v) {
        if (isOperatorValid(display.getText().toString())) {
            switch (v.getId()) {
                case R.id.btnAdd:
                    display.append("+");
                    break;
                case R.id.btnDiv:
                    display.append("/");
                    break;
                case R.id.btnMulti:
                    display.append("*");
                    break;
            }
        }
    }

    public void Calculate (View v) {
        String str = display.getText().toString();
        if (!str.isEmpty()) {
            DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
            Double calculation;
            try {
                calculation = doubleEvaluator.evaluate(str);
                if (calculation.isNaN() || calculation.isInfinite() || calculation.doubleValue() == 0.0) {
                    display.setText("0");
                } else {
                    History history = new History(this);
                    history.writeMessage(str+" = "+String.valueOf(calculation));
                    display.setText(String.valueOf(calculation));
                }
            } catch (Exception e) {
                display.setText(str);
            }
        }
    }

    public void Delete (View v) {
        String string = display.getText().toString();
        if (!string.isEmpty()) {
            int length = string.length();
            display.setText(string.substring(0,length-1));
        }
    }

    public boolean isOperatorValid (String string) {
        if (!string.isEmpty()) {
            int index = string.length()-1;
            char[] strArr = string.toCharArray();

            while (index >= 0) {
                if (isOperator(strArr[index])) {
                    break;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dotIsValid(String string) {
        int index = string.length()-1;
        char[] strArr = string.toCharArray();

        while (index >= 0) {
            if (isOperator(strArr[index])) {
                break;
            } else {
                if (strArr[index] == '.') {
                    return false;
                }
            }
            index--;
        }
        return true;
    }

    public void showHistory (View view){
        startActivity(new Intent("com.example.shadman.calculator.HistoryActivity"));
    }

    public boolean isOperator(char ch) {
        if (ch == '-' || ch == '+' || ch == '/' || ch == '*') {
            return true;
        }
        return false;
    }
}
