package com.example.java_db_test;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class line2Activity extends AppCompatActivity {
    Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line2layout);
    }

    @Override
    public void onBackPressed() {
        startActivity(intentMain);
        finish();
    }
}
