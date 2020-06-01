package com.learnadroid.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


public class timkiem extends AppCompatActivity {

     private EditText checkindate;
     private EditText checkoutdate;
     private ImageButton ggmap;
     private EditText hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);
        getSupportActionBar().hide();

        checkindate = findViewById(R.id.txtCIdate);
        checkoutdate = findViewById(R.id.txtCOdate);
        ggmap = findViewById(R.id.ggmap);

        checkindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, checkin.class);
                intent.putExtra("checkoutdate",checkoutdate.getText().toString());
                intent.putExtra("hotel",hotel.getText().toString());
                startActivity(intent);
            }
        });

        checkoutdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, checkout.class);
                intent.putExtra("checkindate",checkindate.getText().toString());
                intent.putExtra("hotel",hotel.getText().toString());
                startActivity(intent);
            }
        });

        ggmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, GoogleMapAPI.class);
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
