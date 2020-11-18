package com.learnadroid.myfirstapp.dangki;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.database.ConnectionClass;
import com.learnadroid.myfirstapp.gmail.SendMail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class Main2Activity extends AppCompatActivity {

    Button btTieptuc2;
    Button bt1;
    Button huy;
    private String gmail;
    private int keyCf;
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;
    private TextView note;
    private EditText cf;
    private String newId;

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
        setContentView(R.layout.activity_main2);
        btTieptuc2 = (Button) findViewById(R.id.btTieptuc2);
        progressDialog = new ProgressDialog(this);
        connectionClass = new ConnectionClass();
        bt1 = (Button) findViewById(R.id.bt1);
        note = findViewById(R.id.textView2);
        cf = findViewById(R.id.txtKhachsan);
        huy = findViewById(R.id.btHuy);

        Intent intent1 = new Intent();
        gmail = intent1.getStringExtra("gmail");
        gmail = "phamhung99.ltv@gmail.com";
        newId = intent1.getStringExtra("newId");
        newId = "8";
        note.setText("Chúng tôi vừa gửi mã xác nhận bảo mật tới email " +gmail);
        sendEmail();

        btTieptuc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyCf2 = cf.getText().toString().trim();
                if(keyCf2.equals(Integer.toString(keyCf))){
                    Intent mh3 = new Intent(Main2Activity.this, Main3Activity.class);
                    startActivity(mh3);
                } else{
                    Toast.makeText(getBaseContext(),"Mã xác nhận không đúng mời nhập lại",Toast.LENGTH_LONG).show();
                }

            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteUser dl = new DeleteUser();
                dl.execute();
                Intent intent = new Intent(Main2Activity.this, dangki.class);
                startActivity(intent);
            }
        });
    }

    private void sendEmail() {
        Random rd = new Random();
        keyCf = rd.nextInt((9999-10000 +1)+10000);
        //Getting content for email
        String subject = "[COMFIM YOUR BOOIKNGROOM ACCOUNT]";
        String message = Integer.toString(keyCf);

        //Creating SendMail object
        SendMail sm = new SendMail(this, gmail, subject, message);

        //Executing sendmail to send email
        sm.execute();
        Toast.makeText(getBaseContext(),"Email has been sent",Toast.LENGTH_LONG).show();
    }

    public class DeleteUser extends AsyncTask<String, String, String> {

        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int id = 0;
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Please check your internet connection";
                } else {

                    String query1 = "delete from user where id_user =" +newId;
                    String query2 = "delete from customer where id_customer =" +newId;

                    try {
                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(query1);
                    } catch (Exception e) {
                        z = z  + e;
                    }

                    try {
                        Statement stmt2 = con.createStatement();
                        stmt2.executeUpdate(query2);
                    } catch (Exception e) {
                        z = z + e;
                    }

                    z = "delete new ID complete";
                    isSuccess = true;

                }
            } catch (Exception ex) {
                isSuccess = false;
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