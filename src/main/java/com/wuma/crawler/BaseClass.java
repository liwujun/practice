package com.wuma.crawler;

/**
 * Created by user on 17/5/9.
 */
public interface BaseClass<Req,Resp> {
    Class<Resp> getResp();
    Req getRequest();
}
