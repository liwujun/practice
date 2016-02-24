package com.wuma.netty.serializable;

import java.io.Serializable;

/**
 * Created by zhang on 2016/2/24.
 */
public class SubscribeReq implements Serializable {
    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private int subReqID;
    private String userName;
    private String productName;
    private String phoneNumber;
    private String address;

    public String toString() {
        return "SubscribeReq [subReqID=" + subReqID + ",userName=" + userName + ",productName=" +
                productName + ",phoneNumber=" + phoneNumber + ",address=" + address + "]";
    }
}
