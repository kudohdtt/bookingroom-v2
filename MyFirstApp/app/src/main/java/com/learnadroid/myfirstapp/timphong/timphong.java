package com.learnadroid.myfirstapp.timphong;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.roomtype.cacloaiphong;
import com.learnadroid.myfirstapp.timkiemkhachsan.checkin;
import com.learnadroid.myfirstapp.timkiemkhachsan.checkout;

public class timphong extends AppCompatActivity {

    private Button search;
    private EditText checkindate;
    private EditText checkoutdate;

    private TextView ERcheckin;
    private TextView ERcheckout;
    private TextView ERadults;
    private TextView ERchildrent;
    private EditText editAdult;
    private EditText editChildrent;

    private String txt2;
    private String txt3;
    private String txt4;
    private String CIdate;
    private String COdate;

    private Boolean isValidCIdate = false;
    private Boolean isValidCodate = false;
    private Boolean isValidAdults = false;
    private Boolean isValidChildrent = false;

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
        setContentView(R.layout.activity_timphong);
        getSupportActionBar().hide();

        checkindate = findViewById(R.id.textViewCI2);
        checkoutdate = findViewById(R.id.textViewCO2);
        search = findViewById(R.id.buttonSearch2);
        editAdult = findViewById(R.id.editAdult2);
        editChildrent = findViewById(R.id.editChild2);
        search = findViewById(R.id.buttonSearch2);

        ERadults = findViewById(R.id.ERadults2);
        ERchildrent = findViewById(R.id.ERchildrent2);
        ERcheckin = findViewById(R.id.ERcheckin2);
        ERcheckout = findViewById(R.id.ERcheckout2);

        txt2 = checkindate.getText().toString();
        txt3 = checkoutdate.getText().toString();
        txt4 = editAdult.getText().toString();
        //lấy id_hotel mà API gg map và lịch trả về
        Intent intent1 = getIntent();
        final String hotelId = intent1.getStringExtra("hotelId");

        //lấy thời gian checkin checkout mà lịch trả về
        CIdate = intent1.getStringExtra("checkindate");
        checkindate.setText(CIdate);

        COdate = intent1.getStringExtra("checkoutdate");
        checkoutdate.setText(COdate);
        //xử lý sự kiện
        checkindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timphong.this, checkin2.class);
                intent.putExtra("checkoutdate",checkoutdate.getText().toString());
                intent.putExtra("hotel",hotelId);
                startActivity(intent);
            }
        });

        checkoutdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timphong.this, checkout2.class);
                intent.putExtra("checkindate",checkindate.getText().toString());
                intent.putExtra("hotel",hotelId);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidCIdate && !isValidCodate && !isValidAdults && !isValidChildrent && txt2.trim().equals("") && txt3.trim().equals("") && txt4.trim().equals("")){
                    Toast.makeText(getBaseContext(), "Please check all field again !", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(timphong.this, cacloaiphong.class);
                    intent.putExtra("holtelId",hotelId);
                    intent.putExtra("checkindate",CIdate);
                    intent.putExtra("checkoutdate",COdate);
                    startActivity(intent);
                }
            }
        });

        editAdult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ERadults.setText("");
                String txt = editAdult.getText().toString();
                if(!txt.trim().equals("")) {
                    isValidAdults = (!(Integer.parseInt(txt) > 4));
                }
                if (!isValidAdults) {
                    ERadults.setTextColor(Color.rgb(255, 0, 0));
                    ERadults.setText("Nhỏ hơn 5");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editChildrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ERchildrent.setText("");
                String txt = editChildrent.getText().toString();
                if(!txt.trim().equals("")) {
                    isValidChildrent = (!(Integer.parseInt(txt) > 6));
                }
                if (!isValidChildrent) {
                    ERchildrent.setTextColor(Color.rgb(255, 0, 0));
                    ERchildrent.setText("Nhỏ hơn 7");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
