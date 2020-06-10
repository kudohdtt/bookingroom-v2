package com.learnadroid.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CacphongAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Cac_phong> cac_phongList;

    public CacphongAdapter(Context context, int layout, List<Cac_phong> cac_phongList) {
        this.context = context;
        this.layout = layout;
        this.cac_phongList = cac_phongList;
    }

    @Override
    public int getCount() {
        return cac_phongList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout, null);
        //ánh xạ view
        TextView txtTen = (TextView) convertView.findViewById(R.id.txtTen);
        TextView txtThanhpho = (TextView) convertView.findViewById(R.id.txtThanhpho);
        TextView txtTrangthai = (TextView) convertView.findViewById(R.id.txtTrangthai);
        ImageView imgHinh = (ImageView) convertView.findViewById(R.id.imageviewHinh);
        ImageView imgCham = (ImageView) convertView.findViewById(R.id.imgCham);

        // gán giá trị
        Cac_phong cacPhong = cac_phongList.get(position);

        txtTen.setText(cacPhong.getTen());
        txtThanhpho.setText(cacPhong.getThanhpho());
        txtTrangthai.setText(cacPhong.getTrangthai());
        imgHinh.setImageResource(cacPhong.getHinh());
        imgCham.setImageResource(cacPhong.getCham());

        return convertView;
    }
}
