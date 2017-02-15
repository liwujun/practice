package com.wuma.inheri;

/**
 * Created by wuma
 * on 2017/2/15 at 10:09
 */
public class Transformer extends People implements Practisee {
    public Transformer(){
        System.out.println("this is a transformer");
    }
    public void learn(){
        System.out.println("transformer learn is easy");
    }
}
