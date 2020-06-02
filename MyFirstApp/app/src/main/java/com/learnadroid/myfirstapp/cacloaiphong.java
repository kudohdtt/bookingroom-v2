package com.learnadroid.myfirstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class cacloaiphong extends AppCompatActivity {

    ListView listViewLoaiphong;
    ArrayList<LoaiPhong> arrayloaiphong;
    LoaiPhongAdapter adapter;
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;
    LoaiPhong typeRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cacloaiphong);
        getSupportActionBar().hide();

        listViewLoaiphong = (ListView) findViewById(R.id.listLoaiphong);
        arrayloaiphong = new ArrayList<>();

        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);

        adapter = new LoaiPhongAdapter(this, R.layout.mau_loai_phong, arrayloaiphong);

        Intent intent = getIntent();
        int hotelId = intent.getIntExtra("hotelId", -1);
        Toast.makeText(getApplicationContext(), "" + hotelId, Toast.LENGTH_LONG).show();
        Anhxa ax = new Anhxa();
        ax.execute();
        listViewLoaiphong.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoaiPhong tr = arrayloaiphong.get(position);
                Toast.makeText(cacloaiphong.this,"" +position,Toast.LENGTH_LONG).show();
            }
        });

    }

    private class Anhxa extends AsyncTask<String, String, String> {

        String z = "Select room type.";

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
                    String query= "SELECT * FROM roomtype ";

                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            String name = rs.getString(2);
                            float price = rs.getFloat(3);
                            float sale = rs.getFloat(4);
                            String description = rs.getString(5);
                            String bedType = rs.getString(6);
                            Float acreage = rs.getFloat(7);
                            float pricesale = price - sale*price;
                            String image = rs.getString(8);
                            int id_image = getResources().getIdentifier(image,"drawable",getPackageName());
                            typeRoom = new LoaiPhong(name,bedType,"Diện tích : "+acreage+" m2",description,"Giá: "+price+"đ","Khuyến mại "+sale*100+"% : "+pricesale+"đ", id_image);
                            arrayloaiphong.add(typeRoom);
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

            Intent intent = getIntent();
            int hotelId = intent.getIntExtra("hotelId", -1);
            Toast.makeText(getApplicationContext(), "" + hotelId, Toast.LENGTH_LONG).show();



            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
            listViewLoaiphong.setAdapter(adapter);
            progressDialog.hide();
        }
    }
}
