package com.learnadroid.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class cacloaiphong extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cacloaiphong);
        Intent intent = getIntent();
        int hotelId = intent.getIntExtra("hotelId", -1);
        Toast.makeText(getApplicationContext(), "" + hotelId, Toast.LENGTH_LONG).show();


    }
}
