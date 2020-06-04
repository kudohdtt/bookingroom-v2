package com.learnadroid.myfirstapp.roomtype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangki.Main2Activity;
import com.learnadroid.myfirstapp.databse.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Xacnhan extends AppCompatActivity {

    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

    private String hotelId;
    private String roomtypeId;
    private String checkindate;
    private String checkoutdate;

    private Button back;
    private Button cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xacnhan);
        getSupportActionBar().hide();

        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);

        back = findViewById(R.id.back);
        cf = findViewById(R.id.cf);

        Intent intent = getIntent();
        roomtypeId = intent.getStringExtra("roomtypeId");
        hotelId = intent.getStringExtra("hotelId");
        checkindate = intent.getStringExtra("checkindate");
        checkoutdate = intent.getStringExtra("checkoutdate");
        hotelId = "0";
        roomtypeId = "1";
        anhxa ax = new anhxa();
        ax.execute();

        //xet su kien
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Xacnhan.this, cacloaiphong.class));
            }
        });

        cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Xacnhan.this, Main2Activity.class));
            }
        });
    }

    private class anhxa extends AsyncTask<String, String, String> {

        String z = "Load complete";

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Please check your internet connection";
                } else {

                    //lay id
                    String query1 = "SELECT * FROM hotel where id_hotel = " + hotelId;
                    String query2 = "SELECT * FROM roomtype where id_roomtype = " + roomtypeId;
                    String query3 = "SELECT * FROM room where id_roomtype = " + roomtypeId +" and id_hotel = " +hotelId+" and status = 'ready' " +" limit 1";
                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs1 = stmt.executeQuery(query1);
                        Statement stmt2 = con.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery(query3);

                        //hotel
                        while (rs1.next()) {

                            String name = rs1.getString(2);
                            String address = rs1.getString(4);
                            String star = rs1.getString(5);
                            String image = rs1.getString(6);
                            int id_city = rs1.getInt(7);
                            int id_image = getResources().getIdentifier(image,"drawable",getPackageName());
                        }

                        while (rs2.next()){
                            String roomtype = rs2.getString(2);
                            String bedtype = rs2.getString(6);
                            float price = rs2.getFloat(3);
                            float acreage = rs2.getFloat(7);
                        }

                        while (rs3.next()){
                            int id_room = rs3.getInt(1);
                            String roomNumber = rs3.getString(2);
                        }
                    } catch (Exception e) {
                        z = "Exceptions: " + e;
                    }

                }
            } catch (Exception ex) {

                z = "Exceptions" + ex;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();

            progressDialog.hide();
        }
    }
}
