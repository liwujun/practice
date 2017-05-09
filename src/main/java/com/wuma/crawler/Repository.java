package com.wuma.crawler;

/**
 * Created by user on 17/5/9.
 */
public class Repository {
    // <Req, Resp> 不加这个会有错
    public <Req, Resp> Resp getSomething(BaseClass<Req, Resp> baseClass) {
        return null;
    }
}
