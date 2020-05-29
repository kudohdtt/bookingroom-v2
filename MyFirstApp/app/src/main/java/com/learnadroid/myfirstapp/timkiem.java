package com.learnadroid.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class timkiem extends AppCompatActivity {

     private ImageButton checkin;
     private ImageButton checkout;
     private TextView checkindate;
     private TextView checkoutdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);
        getSupportActionBar().hide();

        checkin = findViewById(R.id.imageButtonCI);
        checkout = findViewById(R.id.imageButtonCO);
        checkindate = findViewById(R.id.txtCIdate);
        checkoutdate = findViewById(R.id.txtCOdate);

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, checkin.class);
                startActivity(intent);
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, checkout.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String CIdate = intent.getStringExtra("checkindate");
        checkindate.setText(CIdate);
        String COdate = intent.getStringExtra("checkoutdate");
        checkoutdate.setText(COdate);

    }
}
