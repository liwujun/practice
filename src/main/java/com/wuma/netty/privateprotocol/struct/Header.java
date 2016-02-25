package com.wuma.netty.privateprotocol.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liwujun
 * on 2016/2/25 at 10:56
 */
public final class Header {
    private int crcCode = 0xabef0101;
    private int length;
    private long sessionID;
    private byte type;
    private byte priority;
    private Map<String, Object> attachment = new HashMap<String, Object>();

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public final int getCrcCode() {
        return crcCode;
    }

    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final long getSessionID() {
        return sessionID;
    }

    public final void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public String toString() {
        return "Header [crcCode=" + crcCode + ", length=" + length
                + ",sessionID=" + sessionID + ",type=" + type
                + ",priority=" + priority + ",attachment=" + attachment + "]";
    }
}
