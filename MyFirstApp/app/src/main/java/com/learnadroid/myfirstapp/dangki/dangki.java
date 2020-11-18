package com.learnadroid.myfirstapp.dangki;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.learnadroid.myfirstapp.dangnhap.MainActivity;
import com.learnadroid.myfirstapp.database.ConnectionClass;
import com.learnadroid.myfirstapp.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class dangki extends AppCompatActivity {

    public static final String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]{2,}";
    public static final String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%,.]).{6,20})";
    public static final String phonePattern ="(09|05)+([0-9]{8})\\b";
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;
    private Button btTieptuc;
    private EditText txtMail;
    private EditText txtPass;
    private TextView txtValidate;
    private TextView txtKT;
    private TextView notpass;
    private TextView notphone;
    private EditText txtHoten;
    private EditText txtTen;
    private EditText txtPhone;
    private EditText rePass;
    private CheckBox checkBox;
    private String newId;
    //Validation
    private Boolean isValidMail = false;
    private Boolean isValidpPass = false;
    private Boolean isPhone = false;
    private Boolean isPass = false;


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
        setContentView(R.layout.activity_dangki);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtKT = findViewById(R.id.txtKT);
        txtMail = findViewById(R.id.txtMail);
        txtPass = findViewById(R.id.txtPass);
        txtValidate = findViewById(R.id.txtValidate);
        btTieptuc = findViewById(R.id.btTieptuc);
        txtHoten = findViewById(R.id.txtHoten);
        txtTen = findViewById(R.id.txtTen);
        txtPhone = findViewById(R.id.txtPhone);
        notphone = findViewById(R.id.notphone);
        rePass = findViewById(R.id.txtRepass);
        notpass = findViewById(R.id.notpass);
        checkBox = findViewById(R.id.check);


        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);

        btTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doregister doregister = new Doregister();
                doregister.execute();
            }
        });
        txtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtKT.setText("");
                String pass1 = txtPass.getText().toString().trim();
                isValidpPass = (pass1.matches(passPattern) && pass1.length() > 0);
                if (!isValidpPass) {
                    txtKT.setTextColor(Color.rgb(255, 0, 0));
                    txtKT.setText("0-9 && a-z && A-Z && @#$%.,");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notphone.setText("");
                String phone1 = txtPhone.getText().toString().trim();
                isPhone = (phone1.matches(phonePattern) && phone1.length() > 0);
                if (!isPhone) {
                    notphone.setTextColor(Color.rgb(255, 0, 0));
                    notphone.setText("not a phone number!!");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //validation
        txtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtValidate.setText("");
                String email1 = txtMail.getText().toString().trim();
                isValidMail = (email1.matches(emailPattern) && email1.length() > 0);
                if (!isValidMail) {
                    txtValidate.setTextColor(Color.rgb(255, 0, 0));
                    txtValidate.setText("Email không hợp lệ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notpass.setText("");
                String pass2 = rePass.getText().toString().trim();
                String truepass = txtPass.getText().toString().trim();
                isPass = ( truepass.equals(pass2) && pass2.length() > 0);
                if (!isPass) {
                    notpass.setTextColor(Color.rgb(255, 0, 0));
                    notpass.setText("Mật khẩu không khớp !!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public class Doregister extends AsyncTask<String, String, String> {

        final String name = txtHoten.getText().toString();
        final String email = txtMail.getText().toString();
        final String phone = txtPhone.getText().toString();
        final String username = txtTen.getText().toString();
        final String password = txtPass.getText().toString();


        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            if (!isPass || !isPhone || !isValidMail || !isValidpPass || name.trim().equals("") || email.trim().equals("") || phone.trim().equals("") ||
                    username.trim().equals("") || password.trim().equals("") || rePass.getText().toString().trim().equals("") || !checkBox.isChecked()) {

                z = "Please enter all fields....";

            } else {

                int id = 0;
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Please check your internet connection";
                    } else {

                        //lay id
                        String queryId = "SELECT * FROM customer ORDER BY id_customer DESC LIMIT 1";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(queryId);
                        while (rs.next()) {
                            id = Integer.parseInt(rs.getString(1));
                            id += 1;
                        }

                        newId = Integer.toString(id);

                        String query1 = "insert into customer values('" + newId + "','" + name + "','" + email + "','" + phone + "')";
                        String query2 = "insert into user values('" + newId + "','" + username + "','" + password + "','" + newId + "')";

                        try {
                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(query1);
                        } catch (Exception e) {
                            z = z + ";Exceptions1" + e;
                        }

                        try {
                            Statement stmt2 = con.createStatement();
                            stmt2.executeUpdate(query2);
                        } catch (Exception e) {
                            z = z + ";Exceptions2" + e;
                        }

                        z = "Email has been sent";
                        isSuccess = true;

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
            Toast.makeText(getBaseContext(), "id:" + newId +"name : "+ name + "email:" +email +"phone : "+phone +"username :"+username +"password :" +password , Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
                progressDialog.hide();
                if (isSuccess) {
                    Intent intent = new Intent(dangki.this, Main2Activity.class);
                    intent.putExtra("newId",newId);
                    intent.putExtra("gmail",email);
                    if(progressDialog != null){
                        progressDialog.dismiss();
                    }
                    startActivity(intent);
                }

            }
        }
    }

