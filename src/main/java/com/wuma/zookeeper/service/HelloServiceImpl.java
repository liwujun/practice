package com.wuma.zookeeper.service;

import com.wuma.zookeeper.annotation.RpcService;

/**
 * Created by liwujun on 16/2/28.
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "hello "+name+" !";
    }
}
