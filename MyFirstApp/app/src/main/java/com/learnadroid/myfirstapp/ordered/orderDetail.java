package com.learnadroid.myfirstapp.ordered;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;
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
import com.learnadroid.myfirstapp.database.ConnectionClass;
import com.learnadroid.myfirstapp.roomtype.roomTypeResult;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;


public class orderDetail extends AppCompatActivity {

    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

    private String hotelId;
    private int roomtypeId;
    private Date checkindate;
    private Date checkoutdate;
    private String customerId;
    private int roomId;
    private int keyId;
    private String state;

    private Button back;
    private Button cancelTheRoom;
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
    private TextView key;

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
        setContentView(R.layout.activity_order_detail);

        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);

        back = findViewById(R.id.back);
        cancelTheRoom = findViewById(R.id.cf);
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
        key = findViewById(R.id.textView18);

        Intent intent1 = getIntent();
        customerId = intent1.getStringExtra("customerId");
        //chưa có danh sách khách sạn đã đặt phòng
        customerId = "0";
        hotelId = intent1.getStringExtra("hotelId");
        hotelId = "0";
        anhxa ax = new anhxa();
        ax.execute();

        //xet su kien
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(orderDetail.this, roomTypeResult.class));
            }
        });
        // hủy phòng
        cancelTheRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state.trim().equals("wait for checkin")){
                    huyphong hp = new huyphong();
                    hp.execute();
                }else {
                    Toast.makeText(getBaseContext(), "Room reservation has expired!", Toast.LENGTH_LONG).show();
                }
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
                    String query3 = "SELECT * FROM booking where id_customer = " +customerId;

                    try {
                        //lay thong tin khach san
                        Statement stmt = con.createStatement();
                        ResultSet rs1 = stmt.executeQuery(query1);
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
                        // lay thong tin don phong
                        Statement stmt4 = con.createStatement();
                        ResultSet rs4 = stmt4.executeQuery(query3);

                        while (rs4.next()) {
                            keyId = rs4.getInt(1);
                            key.setText("BK2020KEY"+keyId);
                            checkindate = rs4.getDate(2);
                            cindate.setText("Check in : "+ checkindate.toString().split(" ")[0]);
                            codate.setText("Check out : " +checkoutdate.toString().split(" ")[0]);
                            checkoutdate = rs4.getDate(3);
                            roomId = rs4.getInt(4);
                            state = rs4.getString(6);
                        }
                        //lay thong tin phong dat
                        String query4 = "select *from room where id_room = "+roomId;
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery(query4);
                        while (rs3.next()){
                            roomtypeId = rs3.getInt(3);
                            String roomNumber = rs3.getString(2);
                            number.setText("Số phòng : "+roomNumber);
                        }
                        //lay thong tin loai phong
                        String query2 = "SELECT * FROM roomtype where id_roomtype = " + roomtypeId;
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
            Toast.makeText(getBaseContext(), "" + state, Toast.LENGTH_LONG).show();
            progressDialog.hide();

        }
    }

    private class huyphong extends AsyncTask<String, String, String> {

        String z = "Room cancellation successfully!";

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Please check your internet connection";
                } else {


                    String query = "update booking set state = 'cancel' where id_booking =" +keyId;

                    try {

                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(query);

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
