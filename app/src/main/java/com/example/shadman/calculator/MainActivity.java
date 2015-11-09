package com.example.shadman.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText display;
    DoubleEvaluator calc;
    boolean clearFlag = false;
    static final String PREF_FILE = "Calculator_Pref_File";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (EditText) findViewById(R.id.textViewDisplay);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void GetNum(View v) {
        if (!clearFlag) {
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
    }

    public void GetDot(View v) {
        if (!clearFlag) {
            if(isDotValid(display.getText().toString())) {
                display.append(".");
            }
        }
    }

    public void GetSub(View v) {
        if (!clearFlag) {
            String str = display.getText().toString();
            if(str.isEmpty() && !isOperatorValid(str)) {
                display.append("-");
            } else {
                if (isOperatorValid(str)) {
                    display.append("-");
                }
            }
        }
    }

    public void GetOperator (View v) {
        if (!clearFlag) {
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
    }

    public void Get_SOpertator(View v) {
        if (!clearFlag) {
            String str = display.getText().toString();
            if(str.isEmpty()) {
                switch (v.getId()) {
                    case R.id.btnSine:
                        display.append("sin(");
                        break;
                    case R.id.btnCos:
                        display.append("cos(");
                        break;
                    case R.id.btnTan:
                        display.append("tan(");
                        break;
                    case R.id.btnLog:
                        display.append("log(");
                        break;
                }
            } else {
                int index = str.length()-1;
                switch (v.getId()) {
                    case R.id.btnSine:
                        if(isOperator(str.charAt(index))){
                            display.append("sin(");
                        } else if (isNumber(str) || str.charAt(index) == ')') {
                            display.append("*sin(");
                        }
                        break;
                    case R.id.btnCos:
                        if(isOperator(str.charAt(index))){
                            display.append("cos(");
                        } else if (isNumber(str) || str.charAt(index) == ')') {
                            display.append("*cos(");
                        }
                        break;
                    case R.id.btnTan:
                        if(isOperator(str.charAt(index))){
                            display.append("tan(");
                        } else if (isNumber(str) || str.charAt(index) == ')') {
                            display.append("*tan(");
                        }
                        break;
                    case R.id.btnLog:
                        if(isOperator(str.charAt(index))){
                            display.append("log(");
                        } else if (isNumber(str) || str.charAt(index) == ')') {
                            display.append("*log(");
                        }
                        break;
                }
            }
        }
    }

    public void GetSquare(View v) {
        if (!clearFlag) {
            if(!display.getText().toString().isEmpty()) {
                String str = display.getText().toString();
                int lastIndex = str.length()-1;
                if(isNumber(str) || str.charAt(lastIndex) == ')') {
                    display.append("^");
                }
            }
        }
    }

    public void GetEndBrace(View v) {
        if (!clearFlag) {
            if(isEndBraceValid(display.getText().toString())) {
                display.append(")");
            }
        }
    }

    public void GetCalculation(View v) {
        if(!clearFlag) {
            Calculate();
        }
        /*String str = display.getText().toString();
        if (isOperationValid(str)) {
            DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
            Double calculation;
            try {
                calculation = doubleEvaluator.evaluate(str);
                if (calculation.isNaN() || calculation.isInfinite()) {
                    display.setText("");
                } else {
                    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
                    History history = new History(this);
                    history.writeMessage(currentDateTime + ": " + str + " = " + String.valueOf(calculation) + "\n");
                    display.setText(String.valueOf(calculation));
                }
            }catch (IllegalArgumentException e) {
                display.setText(str);
            } catch (Exception e) {
                display.setText("");
            }
        }*/
    }


    public void Calculate() {
        String str = display.getText().toString();
        if (isOperationValid(str)) {
            DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
            Double calculation;
            try {
                calculation = doubleEvaluator.evaluate(str);
                if (calculation.isNaN() || calculation.isInfinite()) {
                    display.setText("Math Error!");
                    clearFlag = true;
                } else {
                    DecimalFormat decimalFormat = new DecimalFormat("#.######");
                    display.setText(String.valueOf(decimalFormat.format(calculation)));
                    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
                    History history = new History(this);
                    history.writeMessage(currentDateTime + ": " + str + " = " + String.valueOf(decimalFormat.format(calculation)) + "\n");
                }
            }catch (IllegalArgumentException e) {
                if(isEndBraceValid(str)) {
                    display.append(")");
                    Calculate();
                } else if (isOperator(str.charAt(str.length()-1))) {
                    display.setText("Syntex Error!");
                    clearFlag = true;
                } else {
                    display.setText("Math Error!");
                    clearFlag = true;
                }
            } catch (Exception e) {
                display.setText("");
            }
        }
    }

    public void Delete (View v) {
        String string = display.getText().toString();
        if (!string.isEmpty()) {
            int length = string.length();
            int index = length-1;
            if(string.charAt(index) == '(')
            {
                display.setText(string.substring(0,length-4));
            } else if (string.charAt(index) == '!') {
                display.setText("");
                clearFlag = false;
            } else {
                display.setText(string.substring(0,length-1));
            }
        }
    }

    public void showHistory (View view){
        startActivity(new Intent("com.example.shadman.calculator.HistoryActivity"));
    }


    public boolean isOperatorValid (String string) {
        if (!string.isEmpty()) {
            int index = string.length()-1;
            char[] strArr = string.toCharArray();

            while (index >= 0) {
                if (isOperator(strArr[index]) || strArr[index] == '.') {
                    break;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDotValid(String string) {
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

    public boolean isOperator(char ch) {
        if (ch == '-' || ch == '+' || ch == '/' || ch == '*') {
            return true;
        }
        return false;
    }

    public boolean isOperationValid(String str) {
        if(!str.isEmpty()) {
            int len = str.length()-1;
            char[] chrArr = str.toCharArray();
            while (len >= 0) {
                if(isOperator(chrArr[len]) || chrArr[len] == ')' || chrArr[len] == '^' || chrArr[len] == '(')
                {
                    return true;
                }
                len--;
            }
            return false;
        }
        return false;
    }

    public boolean isNumber(String str) {
        if (!str.isEmpty()) {
            int index = str.length()-1;
            if(str.charAt(index) == '0' || str.charAt(index) == '1' || str.charAt(index) == '2' ||
                    str.charAt(index) == '3' || str.charAt(index) == '4' || str.charAt(index) == '5' ||
                    str.charAt(index) == '6' || str.charAt(index) == '7' || str.charAt(index) == '9') {
                return true;
            }
        }
        return false;
    }

    public boolean isEndBraceValid(String str) {
        if(!str.isEmpty()) {
            char[] chArr = str.toCharArray();
            int len = str.length()-1;
            int countOpen = 0;
            int countClose = 0;
            while (len >= 0) {
                if (chArr[len] == '('){
                    countOpen++;
                } else if (chArr[len] == ')') {
                    countClose++;
                }
                len--;
            }
            if (countOpen > countClose) {
                return true;
            }
            return false;
        }
        return false;
    }
}
