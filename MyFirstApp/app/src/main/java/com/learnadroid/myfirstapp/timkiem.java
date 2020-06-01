package com.learnadroid.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class timkiem extends AppCompatActivity {

     private EditText checkindate;
     private EditText checkoutdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);
        getSupportActionBar().hide();

        checkindate = findViewById(R.id.txtCIdate);
        checkoutdate = findViewById(R.id.txtCOdate);

        checkindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, checkin.class);
                startActivity(intent);
            }
        });

        checkoutdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, checkout.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String CIdate = intent.getStringExtra("checkindate");
        if(!CIdate.equals("")){
            checkindate.setText(CIdate);
        }
        String COdate = intent.getStringExtra("checkoutdate");
        checkoutdate.setText(COdate);

    }
}
