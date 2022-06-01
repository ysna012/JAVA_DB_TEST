package com.example.java_db_test;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class line8Activity extends AppCompatActivity {
    Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line8layout);
    }

    @Override
    public void onBackPressed() {
        startActivity(intentMain);
        finish();
    }
}
