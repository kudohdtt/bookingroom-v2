package com.learnadroid.myfirstapp.roomtype;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;
import com.learnadroid.myfirstapp.database.ConnectionClass;
import com.learnadroid.myfirstapp.home.SearchHotel;
import com.learnadroid.myfirstapp.timkiemkhachsan.maXacNhan;

import java.nio.channels.AcceptPendingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class roomResult extends AppCompatActivity {

    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

    private int hotelId;
    private int roomtypeId;
    private String checkindate;
    private String checkoutdate;
    private int id_room;
    private int customerId;

    private Button back;
    private Button cf;
    private ImageView img;
    private TextView hotel_name;
    private TextView hotel_address;
    private TextView hotel_roomtype;
    private TextView bed;
    private TextView dientich;
    private TextView number;
    private TextView cindate;
    private TextView codate;
    private TextView gia;

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
        setContentView(R.layout.activity_xacnhan);

        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);

        back = findViewById(R.id.back);
        cf = findViewById(R.id.cf);
        img = findViewById(R.id.hotelsearch);
        hotel_name = findViewById(R.id.twkhachsan);
        hotel_address = findViewById(R.id.textView2);
        hotel_roomtype = findViewById(R.id.textView);
        bed = findViewById(R.id.textView3);
        dientich = findViewById(R.id.textView4);
        number = findViewById(R.id.textView7);
        cindate = findViewById(R.id.textView15);
        codate = findViewById(R.id.textView8);
        gia = findViewById(R.id.textView10);


        roomtypeId = AccountManager.roomtypeId;
        hotelId = AccountManager.hotelid;
        checkindate = AccountManager.checkindate;
        cindate.setText("Check in : " + checkindate+"/2020");
        checkoutdate = AccountManager.checkoutdate;
        codate.setText("Check out : " + checkoutdate + "/2020");
//        Toast.makeText(getApplicationContext(), "roomtype ID: " + roomtypeId, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), "checkindate: " + checkindate, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), "checkoutdate: " + checkoutdate, Toast.LENGTH_LONG).show();
        anhxa ax = new anhxa();
        ax.execute();

        //xet su kien
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(roomResult.this, roomTypeResult.class));
            }
        });

        cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(roomResult.this, maXacNhan.class);
                startActivity(intent);
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
                    String query3 = "SELECT * FROM room where id_roomtype = '" + roomtypeId +"' and id_hotel = '" +hotelId+"' and status = 'ready' " +" limit 1";
                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs1 = stmt.executeQuery(query1);

                        //hotel
                        while (rs1.next()) {

                            String name = rs1.getString(2);
                            hotel_name.setText("Khách sạn "+ name);
                            String address = rs1.getString(4);
                            hotel_address.setText(address);
                            String star = rs1.getString(5);
                            String image = rs1.getString(6);
                            int id_city = rs1.getInt(9);
                            int id_image = getResources().getIdentifier(image,"drawable",getPackageName());
                            img.setImageResource(id_image);
                        }
                        Statement stmt2 = con.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        while (rs2.next()){
                            String roomtype = rs2.getString(2);
                            hotel_roomtype.setText(roomtype);
                            String bedtype = rs2.getString(6);
                            bed.setText(bedtype);
                            float price = rs2.getFloat(3);
                            float acreage = rs2.getFloat(7);
                            dientich.setText( ""+(int)acreage +" m2");
                            float sale = rs2.getFloat(4);
                            float pricesale = price - sale*price;
                            gia.setText(pricesale+" vnđ");
                        }
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery(query3);
                        while (rs3.next()){
                            id_room = rs3.getInt(1);
                            String roomNumber = rs3.getString(2);
                            number.setText("Số phòng : "+roomNumber);
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
