package com.example.java_db_test;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class line6Activity extends AppCompatActivity {
    Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line6layout);
    }

    @Override
    public void onBackPressed() {
        startActivity(intentMain);
        finish();
    }
}
