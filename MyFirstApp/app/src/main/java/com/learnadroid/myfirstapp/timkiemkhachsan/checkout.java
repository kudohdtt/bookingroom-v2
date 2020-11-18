package com.learnadroid.myfirstapp.timkiemkhachsan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;
import com.learnadroid.myfirstapp.home.MainActivity;

public class checkout extends AppCompatActivity {
    private Button cf;
    private DatePicker datePicker;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        cf = findViewById(R.id.buttonCIdate);
        datePicker = (DatePicker) findViewById(R.id.CheckOutDate);

        cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+1);
                Intent intent = new Intent(checkout.this, MainActivity.class);
                AccountManager.checkoutdate = date;
                startActivity(intent);
            }
        });
    }
}
