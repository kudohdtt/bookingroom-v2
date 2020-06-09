package com.learnadroid.myfirstapp.timkiemkhachsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.actor.Hotel;


import java.util.List;

public class hotelListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Hotel> hotelList;

    public hotelListAdapter(Context context, int layout, List<Hotel> hotelList) {
        this.context = context;
        this.layout = layout;
        this.hotelList = hotelList;
    }

    @Override
    public int getCount() {
        return hotelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(layout, null);
        //anh xa
        TextView name = view.findViewById(R.id.name_hotel);
        RatingBar star = view.findViewById(R.id.rate_hotel);
        ImageView img1 = view.findViewById(R.id.imageView_1);
        ImageView img2 = view.findViewById(R.id.imageView_2);
        ImageView img3 = view.findViewById(R.id.imageView_3);
        TextView city = view.findViewById(R.id.text_location_hotel);
        TextView info = view.findViewById(R.id.gt_hotel);
        TextView km = view.findViewById(R.id.ttkm_hotel);
        TextView price = view.findViewById(R.id.price);

        //gan gia tri
        Hotel hotel = hotelList.get(i);
        name.setText(hotel.getName());
        star.setRating(hotel.getStar());
        img1.setImageResource(hotel.getImage1());
        img2.setImageResource(hotel.getImage2());
        img3.setImageResource(hotel.getImage3());
        city.setText(hotel.getCity());
        info.setText(hotel.getAddress());
        if(hotel.isKm()){
            km.setText("Đang khuyến mại");
        } else {
            km.setText("Chưa có khuyến mại");
        }
        price.setText(""+hotel.getPrice());

        return view;
    }
}
