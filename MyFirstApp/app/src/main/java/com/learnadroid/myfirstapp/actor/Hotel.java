package com.learnadroid.myfirstapp.actor;

public class Hotel {
    private int id;
    private String name;
    private String location;
    private float star;
    private String address;
    private int image1;
    private int image2;
    private int image3;
    private String city;
    private int price;
    private boolean km;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public int getImage3() {
        return image3;
    }

    public void setImage3(int image3) {
        this.image3 = image3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isKm() {
        return km;
    }

    public void setKm(boolean km) {
        this.km = km;
    }

    public Hotel(int id, String name, String location, float star, String address, int image1, int image2, int image3, String city, int price, boolean km) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.star = star;
        this.address = address;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.city = city;
        this.price = price;
        this.km = km;
    }
}
