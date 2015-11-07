package com.example.shadman.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {
    TextView textViewHistory;
    History history = new History(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTextViewHistory();
    }

    public void setTextViewHistory() {
        textViewHistory = (TextView) findViewById(R.id.textViewHistory);
        textViewHistory.setText(history.readMessage());
    }
}
