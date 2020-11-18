package com.learnadroid.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class DsDatphong extends AppCompatActivity {

    ListView listViewCacphong;
    ArrayList<Cac_phong> arraycacphong;
    CacphongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_datphong);

        Anhxa();
        adapter = new CacphongAdapter(this, R.layout.phongkhachsan, arraycacphong);
        listViewCacphong.setAdapter(adapter);
    }

    private void Anhxa() {

        listViewCacphong = (ListView) findViewById(R.id.listviewCacphong);
        arraycacphong = new ArrayList<>();

        arraycacphong.add(new Cac_phong("Khách sạn Media Class", "Đà Nẵng","Wait foeckin", R.drawable.phongcaocap, R.drawable.cham));
        arraycacphong.add(new Cac_phong("Khách sạn Petrol", "Buôn Mê Thuật","Wait for checkin", R.drawable.phongcaocap, R.drawable.cham2));
        arraycacphong.add(new Cac_phong("Khách sạn Hotel", "Hà Nội","Wait for checkin", R.drawable.phongcaocap, R.drawable.cham1));
        arraycacphong.add(new Cac_phong("Khách sạn Meta", "Hà Nam","Wait for checkin", R.drawable.phongcaocap, R.drawable.cham2));
        arraycacphong.add(new Cac_phong("Khách sạn hot", "Hà Nội","Wait for checkin", R.drawable.phongcaocap, R.drawable.cham));
        arraycacphong.add(new Cac_phong("Khách sạn Hotdahfhahfel", "Hà Nội","Wait for checkin", R.drawable.phongcaocap, R.drawable.cham1));
    }
}
