package com.learnadroid.myfirstapp;

public class Cac_phong {
    private String Ten;
    private String Thanhpho;
    private String Trangthai;
    private int Hinh;
    private int Cham;

    public Cac_phong(String ten, String thanhpho, String trangthai, int hinh, int cham) {
        Ten = ten;
        Thanhpho = thanhpho;
        Trangthai = trangthai;
        Hinh = hinh;
        Cham = cham;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getThanhpho() {
        return Thanhpho;
    }

    public void setThanhpho(String thanhpho) {
        Thanhpho = thanhpho;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String trangthai) {
        Trangthai = trangthai;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }
    public int getCham(){
        return Cham;
    }
    public void setCham(int cham){
        Cham = cham;
    }
}
