package com.learnadroid.myfirstapp.roomtype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.roomtype.LoaiPhong;

import java.util.List;

public class LoaiPhongAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<LoaiPhong> loaiphongList;

    public LoaiPhongAdapter(Context context, int layout, List<LoaiPhong> loaiphongList) {
        this.context = context;
        this.layout = layout;
        this.loaiphongList = loaiphongList;
    }

    @Override
    public int getCount() {
        return loaiphongList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(layout, null);

        //ánh xạ view
        TextView txtTen = (TextView) view.findViewById(R.id.textviewTen);
        TextView txtGiuong = (TextView) view.findViewById(R.id.textviewGiuong);
        TextView txtDientich = (TextView) view.findViewById(R.id.textviewDientich);
        TextView txtMota = (TextView) view.findViewById(R.id.textviewMota);
        TextView txtGia = (TextView) view.findViewById(R.id.textviewGia);
        TextView txtSale = (TextView) view.findViewById(R.id.textviewSale);
        ImageView imgHinh = (ImageView) view.findViewById(R.id.imageviewHinh);

        //gán giá trị
        LoaiPhong loaiphong = loaiphongList.get(i);
        txtTen.setText(loaiphong.getTen());
        txtGiuong.setText(loaiphong.getGiuong());
        txtDientich.setText(loaiphong.getDientich());
        txtMota.setText(loaiphong.getMota());
        txtGia.setText(loaiphong.getGia());
        txtSale.setText(loaiphong.getSale());
        imgHinh.setImageResource(loaiphong.getHinh());

        return view;
    }
}

