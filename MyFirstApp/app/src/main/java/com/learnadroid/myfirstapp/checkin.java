package com.learnadroid.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class checkin extends AppCompatActivity {
    private Button cf;
    private DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        getSupportActionBar().hide();

        cf = findViewById(R.id.buttonCIdate);
        datePicker = (DatePicker) findViewById(R.id.CheckInDate);

        cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth();
                Intent intent = new Intent(checkin.this, timkiem.class);
                intent.putExtra("checkindate",date);
                startActivity(intent);
            }
        });
    }
}
