package com.learnadroid.myfirstapp.timkiemkhachsan;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.learnadroid.myfirstapp.actor.Hotel;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;
import com.learnadroid.myfirstapp.database.ConnectionClass;
import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.roomtype.roomTypeResult;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class hotelResult extends AppCompatActivity {

    ListView ListViewHotel;
    ArrayList<Hotel> ListHotel;
    hotelListAdapter adapter;
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;
    Hotel hotel;
    private String keyword;
    private String checkindate;
    private String checkoutdate;
    private int id_city;
    Button back;

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
        setContentView(R.layout.activity_ketquatimkiem);

        ListViewHotel = (ListView) findViewById(R.id.listHotel);
        ListHotel = new ArrayList<>();
        back = findViewById(R.id.btn_back);

        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);

        adapter = new hotelListAdapter(this, R.layout.dd_kqtk, ListHotel);
        //lấy id_hotel
        keyword = AccountManager.keyword;
        checkindate = AccountManager.checkindate;
        checkoutdate = AccountManager.checkoutdate;

        Toast.makeText(getApplicationContext(), "keyword: " + checkindate, Toast.LENGTH_LONG).show();

        Anhxa ax = new Anhxa();
        ax.execute();

        ListViewHotel.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hotel ht = ListHotel.get(position);
                AccountManager.hotelid = ht.getId();
                Intent intent = new Intent(hotelResult.this, roomTypeResult.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hotelResult.this, timkiem.class);
                startActivity(intent);
            }
        });
    }

    private class Anhxa extends AsyncTask<String, String, String> {

        String z = "Kết quả tìm kiếm : ";
        int kq = 0;

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

                    String query = "SELECT * from hotel where hotel.name ='" + keyword+"' or exists(select id_city from city where city.name ='"+keyword+"')";
                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                            while (rs.next()) {
                                kq+=1;
                                int id = rs.getInt(1);
                                String name = rs.getString(2);
                                String location = rs.getString(3);
                                String address = rs.getString(4);
                                float star = rs.getFloat(5);
                                String image1 = rs.getString(6);
                                int id_image1 = getResources().getIdentifier(image1,"drawable",getPackageName());
                                String image2 = rs.getString(7);
                                int id_image2 = getResources().getIdentifier(image2,"drawable",getPackageName());
                                String image3 = rs.getString(8);
                                int id_image3 = getResources().getIdentifier(image3,"drawable",getPackageName());
                                int id_city = rs.getInt(9);
                                int price = rs.getInt(10);
                                Boolean km = rs.getBoolean(11);
                                String city = "";

                                String query2 = "select * from city where id_city ='" + id_city + "'";
                                try{
                                    Statement st1 = con.createStatement();
                                    ResultSet rs1 = st1.executeQuery(query2);
                                    while (rs1.next()){
                                        city = rs1.getString(2);
                                    }
                                }catch (Exception e){
                                    z= z+e;
                                }
                                hotel = new Hotel(id,name,location,star,address,id_image1,id_image2,id_image3,city,price,km);
                                ListHotel.add(hotel);
                            }
                        z += kq;

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
            ListViewHotel.setAdapter(adapter);
            progressDialog.hide();
        }
    }
}
