package com.learnadroid.myfirstapp.actor;

public class roomType {
    private int id;
    private String Ten;
    private String Giuong;
    private String Dientich;
    private String Mota;
    private String Gia;
    private String Sale;
    private int Hinh;



    public roomType(int id, String ten, String giuong, String dientich, String mota, String gia, String sale, int hinh) {
        this.id = id;
        Ten = ten;
        Giuong = giuong;
        Dientich = dientich;
        Mota = mota;
        Gia = gia;
        Sale = sale;
        Hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getGiuong() {
        return Giuong;
    }

    public void setGiuong(String giuong) {
        Giuong = giuong;
    }

    public String getDientich() {
        return Dientich;
    }

    public void setDientich(String dientich) {
        Dientich = dientich;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getSale() {
        return Sale;
    }

    public void setSale(String sale) {
        Sale = sale;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }
}

