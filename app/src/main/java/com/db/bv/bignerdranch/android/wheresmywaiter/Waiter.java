package com.db.bv.bignerdranch.android.wheresmywaiter;

/**
 * Created by Owner on 4/26/2018.
 */

public class Waiter {

    private String restarauntId;
    private String waiterId;
    private String firstName;
    private String lastName;
    private String password;

    public Waiter(){}

    public Waiter(String restarauntId, String waiterId, String firstName, String lastName, String password){

        this.restarauntId = restarauntId;
        this.waiterId = waiterId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getRestarauntId() {
        return restarauntId;
    }

    public String getWaiterId() {
        return waiterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

}
