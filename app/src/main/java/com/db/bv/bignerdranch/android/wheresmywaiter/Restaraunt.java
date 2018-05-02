package com.db.bv.bignerdranch.android.wheresmywaiter;

/**
 * Created by bjvel on 4/18/2018.
 */

public class Restaraunt {

    private String id, name, address, zip, city, state;


    public Restaraunt(){};

    public Restaraunt(String id, String name, String address, String zip,String city, String state)
    {
        this.id = id;
       this.name = name;
       this.address = address;
       this.zip = zip;
       this.city = city;
       this.state = state;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zipCode) {
        this.zip = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
