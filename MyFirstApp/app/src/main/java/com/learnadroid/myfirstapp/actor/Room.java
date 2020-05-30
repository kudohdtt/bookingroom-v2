package com.learnadroid.myfirstapp.actor;

public class Room {
    private int id;
    private String roomNumber;
    private int price;
    private String des;

    public Room(int id, String roomNumber, int price, String des) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.price = price;
        this.des = des;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}

