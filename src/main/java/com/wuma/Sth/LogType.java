package com.wuma.Sth;

/**
 * Created by user on 17/3/30.
 * 貌似是一种很巧妙的方法
 */
public enum LogType implements IEnumCode<LogType>{
    ORDER(0),
    REFUND(1);
    private int code;
    private LogType(int code){
        this.code=code;
    }

    public int getCode() {
        return this.code;
    }
    public LogType parse(int code){
        LogType[] arr$=values();
        int len=arr$.length;
        for (int i$=0;i$<len;++i$){
            LogType logType=arr$[i$];
            if (logType.getCode()==code){
                return logType;
            }
        }
        return null;
    }
}
