package com.wuma.netty.serializable;

import java.io.Serializable;

/**
 * Created by zhang on 2016/2/24.
 */
public class SubscribeResp implements Serializable {

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private int subReqID;
    private int respCode;
    private String desc;

    public String toString() {
        return "SubscribeResp [subReqID=" + subReqID + ",respCode=" + respCode
                + ",desc=" + desc + "]";
    }
}
