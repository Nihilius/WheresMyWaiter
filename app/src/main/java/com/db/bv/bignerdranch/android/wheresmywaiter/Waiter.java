package com.db.bv.bignerdranch.android.wheresmywaiter;

/**
 * Created by Owner on 4/26/2018.
 */

public class Waiter {

    private String restarauntId;
    private String waiterId;
    private String password;

    public Waiter(){}

    public Waiter(String restarauntId, String waiterId, String password){

        this.restarauntId = restarauntId;
        this.waiterId = waiterId;
        this.password = password;
    }

    public void setRestarauntId(String restarauntId) {
        this.restarauntId = restarauntId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRestarauntId() {
        return restarauntId;
    }

    public String getWaiterId() {
        return waiterId;
    }

    public String getPassword() {
        return password;
    }

}
