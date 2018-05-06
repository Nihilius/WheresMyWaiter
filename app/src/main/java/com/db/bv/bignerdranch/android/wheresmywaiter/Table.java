package com.db.bv.bignerdranch.android.wheresmywaiter;

/**
 * Created by Owner on 5/1/2018.
 */

public class Table {

    String restarauntId, waiterId, customerRequest;
    Boolean isPinged, hasMessage;
    int tableNumber;

    public Table(){}

    public Table(int tableNumber, String restarauntId, String waiterId,
                 Boolean isPinged, Boolean hasMessage, String customerRequest){
        this.tableNumber = tableNumber;
        this.restarauntId = restarauntId;
        this.waiterId = waiterId;
        this.isPinged = isPinged;
        this.hasMessage = hasMessage;
        this.customerRequest = customerRequest;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getRestarauntId() {
        return restarauntId;
    }

    public void setRestarauntId(String restarauntId) {
        this.restarauntId = restarauntId;
    }

    public String getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }

    public String getCustomerRequest() {
        return customerRequest;
    }

    public void setCustomerRequest(String customerRequest) {
        this.customerRequest = customerRequest;
    }
    public Boolean getIsPinged() {
        return isPinged;
    }
    public void setIsPinged(Boolean pinged) {
        isPinged = pinged;
    }

    public Boolean getHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(Boolean hasMessage) {
        this.hasMessage = hasMessage;
    }
}
