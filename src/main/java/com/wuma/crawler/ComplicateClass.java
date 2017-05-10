package com.wuma.crawler;

/**
 * Created by user on 17/5/9.
 */
public class ComplicateClass<Req, Resp> implements BaseClass<Req, Resp> {

    Req req;
    public Class<Resp> getResp() {
        return null;
    }

    public Req getRequest() {
        return null;
    }
    public <T> void showElemets(T... args){
//        T[] array=new T[10];
        //Type parameter cannot be instantiated directly
    }

    public void constructorF(){
//        req=new Req();
    }
    //<T>用来声明这个函数与泛型有关
    public <T> T showElemets(){
        return null;
    }
}
