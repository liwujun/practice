package com.wuma.netty.memcached;

/**
 * Created by liwujun
 * on 2016/2/18 at 17:14
 */
public class MemcachedResponse {
    private byte magic;
    private byte opCode;
    private byte dataType;
    private short status;
    private int id;
    private long cas;
    private int flags;
    private int expires;
    private String key;
    private String data;

    public MemcachedResponse(byte magic, byte opCode, byte dataType, short status,
                             int id, long cas, int flags, int expires, String key, String data) {
        this.magic = magic;
        this.opCode = opCode;
        this.dataType = dataType;
        this.status = status;
        this.id = id;
        this.cas = cas;
        this.flags = flags;
        this.expires = expires;
        this.key = key;
        this.data = data;
    }

    public byte getMagic() {
        return magic;
    }

    public byte getOpCode() {
        return opCode;
    }

    public byte getDataType() {
        return dataType;
    }

    public short getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public long getCas() {
        return cas;
    }

    public int getFlags() {
        return flags;
    }

    public int getExpires() {
        return expires;
    }

    public String getKey() {
        return key;
    }

    public String getData() {
        return data;
    }
}
