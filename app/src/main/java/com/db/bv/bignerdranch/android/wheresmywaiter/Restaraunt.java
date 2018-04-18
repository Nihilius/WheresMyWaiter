package com.db.bv.bignerdranch.android.wheresmywaiter;

/**
 * Created by bjvel on 4/18/2018.
 */

public class Restaraunt {

    private String name, address, zipCode, state;


    public Restaraunt(){};

    public Restaraunt(String name, String address, String zipCode, String state)
    {
       this.name = name;
       this.address = address;
       this.zipCode = zipCode;
       this.state = state;


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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
