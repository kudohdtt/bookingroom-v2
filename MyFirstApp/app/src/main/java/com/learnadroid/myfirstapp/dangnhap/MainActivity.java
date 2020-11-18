package com.learnadroid.myfirstapp.dangnhap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.learnadroid.myfirstapp.database.ConnectionClass;
import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangki.dangki;
import com.learnadroid.myfirstapp.timkiemkhachsan.timkiem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private TextView linkdangki;
    private Button dangnhap;
    private EditText username;
    private EditText password;

    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

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
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        linkdangki = findViewById(R.id.signup_link);
        dangnhap = findViewById(R.id.bt_afp);
        connectionClass = new ConnectionClass();
        progressDialog = new ProgressDialog(this);
        username = findViewById(R.id.et_afp_email);
        password = findViewById(R.id.et_afp_password);

        //dangki
        linkdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, dangki.class));
            }
        });
        //dangnhap
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dologin dologin = new Dologin();
                dologin.execute();
            }
        });

    }

        private class Dologin extends AsyncTask<String, String, String> {

            String un = username.getText().toString();
            String pass = password.getText().toString();
            String z = "";
            boolean isSuccess = false;

            @Override
            protected void onPreExecute() {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                if (un.trim().equals("") || pass.trim().equals(""))
                    z = "Please enter all fields....";
                else {
                    try {
                        Connection con = connectionClass.CONN();
                        if (con == null) {
                            z = "Please check your internet connection";
                        } else {
                            String queryUser = " select * from user where username='" + un + "'and password = '" + pass + "'";
                            Statement stmt = con.createStatement();
                            ResultSet resultQueryUser = stmt.executeQuery(queryUser);

                            if (resultQueryUser.first()) {
                                isSuccess = true;
                                //z = resultQueryUser.getInt(1) + resultQueryUser.getString(2) + resultQueryUser.getInt(3);
                                String userName = resultQueryUser.getString(2);
                                String passWord = resultQueryUser.getString(3);
                                //z = "Login successfull - Mãi bên nhau bạn nhé!!";

                                int idCustomer = resultQueryUser.getInt(4);
                                AccountManager.customerId = idCustomer;
                                AccountManager.userId = idCustomer;

                                String queryCustomer = " select * from customer where id_customer='" + idCustomer + "'";

                                ResultSet resultQueryCustomer = stmt.executeQuery(queryCustomer);
                                if (resultQueryCustomer.first()) {

                                    int id = resultQueryCustomer.getInt(1);
                                    String email = resultQueryCustomer.getString(3);
                                    AccountManager.gmail = email;
                                    String fulName = resultQueryCustomer.getString(2);
                                    String numberPhone = resultQueryCustomer.getString(4);
                                    String sex = "Male";
                                    String birthDay = "1/1/1999";

                                    AccountManager.getInstance().InitAccount(id, email, fulName, numberPhone, sex, birthDay, userName, passWord);
                                    z = resultQueryCustomer.getInt(1) + " " + resultQueryCustomer.getString(2) + " " + resultQueryCustomer.getString(3);
                                }else {
                                    z = "queryFail";
                                }

                            } else {
                                isSuccess = false;
                                z = "Wrong password or username - Sai mật khẩu hoặc tên tài khoản rồi bạn mình ơi";
                            }
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        z = "Exceptions" + ex;
                    }
                }
                return z;
            }
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
                //fix loi

                if (isSuccess) {
                    Intent intent = new Intent(MainActivity.this, com.learnadroid.myfirstapp.home.MainActivity.class);
                    startActivity(intent);
                }
                progressDialog.hide();
            }
        }

}


