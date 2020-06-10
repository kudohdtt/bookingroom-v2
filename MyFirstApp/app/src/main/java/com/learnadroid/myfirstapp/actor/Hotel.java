package com.learnadroid.myfirstapp.actor;

import java.util.List;

public class Hotel {
    private int id;
    private String name;
    private String location;
    private float rate;
    private List<Room> roomList;

    public Hotel(int id, String name, String location, float rate, List<Room> roomList) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rate = rate;
        this.roomList = roomList;
    }

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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
