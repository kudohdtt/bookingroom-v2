package com.learnadroid.myfirstapp.home;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.learnadroid.myfirstapp.home.ImageConverter;
import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;

public class Profile extends AppCompatActivity {

    EditText fulName;
    EditText userName;
    EditText sex;
    EditText birthDay;
    EditText numberPhone;
    EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.chandung);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);

        ImageView circularImageView = (ImageView) findViewById(R.id.imageView);
        circularImageView.setImageBitmap(circularBitmap);
        ReferenceInit();
        SetDataForTextViews();
    }

    private void ReferenceInit(){
        fulName = findViewById(R.id.txtten);
        userName =  findViewById(R.id.txttendn);
        sex =  findViewById(R.id.txtgioitinh);
        birthDay =  findViewById(R.id.txtngaysin);
        numberPhone =  findViewById(R.id.txtsodienthoai);
        email =  findViewById(R.id.txtemail);
    }

    private void SetDataForTextViews(){
        fulName.setText(AccountManager.getInstance().user.getFullName());
        userName.setText(AccountManager.getInstance().user.getUserName());
        sex.setText(AccountManager.getInstance().user.getSex());
        birthDay.setText(AccountManager.getInstance().user.getBirthDay());
        numberPhone.setText(AccountManager.getInstance().user.getNumberPhone());
        email.setText(AccountManager.getInstance().user.getEmail());
    }

}
